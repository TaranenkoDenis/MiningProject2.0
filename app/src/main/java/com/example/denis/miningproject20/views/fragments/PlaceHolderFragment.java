package com.example.denis.miningproject20.views.fragments;

/**
 * Created by denis on 30.07.17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;
import com.example.denis.miningproject20.views.ConverterHashrate;
import com.example.denis.miningproject20.views.GraphActivity;
import com.example.denis.miningproject20.views.adapters.RecyclerAdatapterItems;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String LOG_TAG = "MY_LOG: " + PlaceHolderFragment.class.getSimpleName();

    private final int FRAGMENT_BASE = 1;
    private final int FRAGMENT_WORKERS = 2;

    private PlaceHolderFragment currentFragment;
    private final String MY_LOG = "MY_LOG: " + PlaceHolderFragment.class.getSimpleName();
    private List<ResponseEthermine> responsesEthermine;

    private TextView tvLastUpdate;
    private TextView tvAverageHashrate;
    private TextView tvCurrentHashrate;
    private TextView tvReportedHashrate;
    private TextView tvWorkers;
    private LineChart lineChart;
    private int currentPosition;

    RecyclerAdatapterItems adapterWorkers;

    public PlaceHolderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceHolderFragment newInstance(int sectionNumber) {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView(): current fragment = " + getArguments().getInt(ARG_SECTION_NUMBER));

        View defaultRootView = inflater.inflate(R.layout.fragment_start, container, false);
        View rootView = null;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

        switch (getArguments().getInt(ARG_SECTION_NUMBER)){

            case FRAGMENT_BASE:
                currentPosition = FRAGMENT_BASE;
                rootView = inflater.inflate(R.layout.fragment_base, container, false);

                TextView tvNameWorker = (TextView) rootView.findViewById(R.id.tv_name_workers_fragment_base);
                TextView tvNameCurrentHashrate = (TextView) rootView.findViewById(R.id.tv_name_current_hashrate_fragment_base);
                TextView tvNameAverageHashrate = (TextView) rootView.findViewById(R.id.tv_name_average_hashrate_fragment_base);
                TextView tvNameReportedHashrate = (TextView) rootView.findViewById(R.id.tv_name_reported_hashrate_fragment_base);
                tvAverageHashrate = (TextView) rootView.findViewById(R.id.tv_average_hashrate_fragment_base);
                tvCurrentHashrate = (TextView) rootView.findViewById(R.id.tv_current_hashrate_fragment_base);
                tvReportedHashrate = (TextView) rootView.findViewById(R.id.tv_reported_hashrate_fragment_base);
                tvWorkers = (TextView) rootView.findViewById(R.id.tv_number_workers_fragment_base);

                setLastResponsesEthermine(databaseHelper.getLastResponsesEthermine());

//                tvNameWorker.setOnClickListener(myClickListener);
//                tvNameCurrentHashrate.setOnClickListener(myClickListener);
//                tvNameAverageHashrate.setOnClickListener(myClickListener);
//                tvNameReportedHashrate.setOnClickListener(myClickListener);
//                tvAverageHashrate.setOnClickListener(myClickListener);
//                tvCurrentHashrate.setOnClickListener(myClickListener);
//                tvReportedHashrate.setOnClickListener(myClickListener);
//                tvWorkers.setOnClickListener(myClickListener);

//                tvWorkers.setOnClickListener();

                tvLastUpdate = (TextView) rootView.findViewById(R.id.tv_last_update);

                lineChart = (LineChart) rootView.findViewById(R.id.graphEthermineHashrate);

                lineChart.setOnClickListener(v -> {
                    startActivity(new Intent(getContext(), GraphActivity.class));
                });
                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.setDragEnabled(false);
                lineChart.setScaleEnabled(false);
                lineChart.setPinchZoom(false);

                List<Entry> series = new ArrayList<>();
                series.add(new Entry(0, 1));
                series.add(new Entry(1, 5));
                series.add(new Entry(2, 3));
                series.add(new Entry(3, 2));
                series.add(new Entry(4, 6));
                LineDataSet dataSet = new LineDataSet(series, "Hashrate");
                dataSet.setColor(getResources().getColor(R.color.colorCurrentHashrate));
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

                break;

            case FRAGMENT_WORKERS:
                currentPosition = FRAGMENT_WORKERS;

                rootView = inflater.inflate(R.layout.fragment_workers, container, false);

                final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_for_workers);
                final LinearLayoutManager verticalLayoutManager =
                        new LinearLayoutManager(rootView.getContext(),
                                LinearLayoutManager.VERTICAL,
                                false);
                recyclerView.setLayoutManager(verticalLayoutManager);
                adapterWorkers = new RecyclerAdatapterItems();

                List<WorkerEthermine> listWorkers = new ArrayList<>();
                for (ResponseEthermine response : databaseHelper.getLastResponsesEthermine())
                    listWorkers.addAll(response.getWorkers().getWorkersEthermine());

                adapterWorkers.addItemsToRecycler(listWorkers);

                recyclerView.setAdapter(adapterWorkers);
                break;
        }


        if(rootView == null)
            return defaultRootView;

        return rootView;
    }

    public void setLastResponsesEthermine(List<ResponseEthermine> listOfLastResponsesEthermine) {
        responsesEthermine = listOfLastResponsesEthermine;
        refillFragmentBaseView();
    }

    private void refillFragmentWorkers() {

        if (responsesEthermine != null ){
            List<WorkerEthermine> list = new ArrayList<>();
            for (ResponseEthermine response : responsesEthermine)
                list.addAll(response.getWorkers().getWorkersEthermine());
            for (WorkerEthermine workerEthermine : list)
                Log.d(LOG_TAG, "Name of worker before adding to adapterWorkers: " + workerEthermine.getWorker());
            adapterWorkers.addWorkersEthermineToRecycler(list);
            adapterWorkers.notifyDataSetChanged();
        }
    }

    private void refillFragmentBaseView() {

        if (responsesEthermine.size() > 0) {

            Log.d(LOG_TAG, "refillFragmentBaseView()");
            Long totalNumberOfWorkers = 0L;
            Long liveWorkers = 0L;
            Double avgHashRate = 0D;
            Double reportedHashRate = 0D;
            Double currentHashRate = 0D;

            String strAvgHashRate;
            String strReportedHashRate;
            String strCurrentHashRate;

            ConverterHashrate converter = ConverterHashrate.getInstance();
            // TODO что, если по одному кошельку у пользователя средний хэшрейт в гига измеряется, а по другому в мега

            for (ResponseEthermine response : responsesEthermine) {

                totalNumberOfWorkers += response.getWorkers().getWorkersEthermine().size();

                avgHashRate += response.getAvgHashrate();
                currentHashRate += converter.convertStringHashRateToDouble(response.getHashRate());
                reportedHashRate += converter.convertStringHashRateToDouble(response.getReportedHashRate());
            }

            long numberElementsInResponses = responsesEthermine.size();

            avgHashRate /= numberElementsInResponses;
            currentHashRate /= numberElementsInResponses;
            reportedHashRate /= numberElementsInResponses;

            setNumberOfWorkers(totalNumberOfWorkers, totalNumberOfWorkers);
            setHashRates(converter.convertDoubleHashRateToString(avgHashRate),
                    converter.convertDoubleHashRateToString(currentHashRate),
                    converter.convertDoubleHashRateToString(reportedHashRate));
        }
        // setCurrency();
        // setGraph();
    }

    private void setHashRates(String avgHashRate, String currentHashRate, String reportedHashRate) {
        tvAverageHashrate.setText(avgHashRate);
        tvCurrentHashrate.setText(currentHashRate);
        tvReportedHashrate.setText(reportedHashRate);
    }

    private void setNumberOfWorkers(Long totalNumberOfWorkers, Long livingWorkers) {

        for (ResponseEthermine response : responsesEthermine)
            totalNumberOfWorkers += response.getWorkers().getWorkersEthermine().size();

        // TODO change this in production
        livingWorkers = totalNumberOfWorkers;

        tvWorkers.setText(totalNumberOfWorkers + "/" + livingWorkers);
    }
}
