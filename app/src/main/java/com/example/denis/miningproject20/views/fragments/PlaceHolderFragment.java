package com.example.denis.miningproject20.views.fragments;

/**
 * Created by denis on 30.07.17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.service.MyService;
import com.example.denis.miningproject20.views.ConverterHashrate;
import com.example.denis.miningproject20.views.activities.GraphActivity;
import com.example.denis.miningproject20.views.adapters.RecyclerAdatapterItems;
import com.example.denis.miningproject20.views.presenters.PresenterStartActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceHolderFragment extends Fragment implements IListenerUpdatesFromServers {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String LOG_TAG = "MY_LOG: " + PlaceHolderFragment.class.getSimpleName();

    private final int FRAGMENT_BASE = 1;
    private final int FRAGMENT_WORKERS = 2;

    private TextView tvLastUpdate;
    private TextView tvAverageHashrate;
    private TextView tvCurrentHashrate;
    private TextView tvReportedHashrate;
    private TextView tvWorkers;
    private LineChart lineChart;
    private Spinner spinner;

    private PresenterStartActivity.IStartActivity activity;
    private PresenterStartActivity mPresenter;

    private static final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

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
        mPresenter = activity.getPresenter();
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case FRAGMENT_BASE:
                rootView = inflater.inflate(R.layout.fragment_base, container, false);

                tvLastUpdate = (TextView) rootView.findViewById(R.id.tv_last_update_fragment_base);
                tvAverageHashrate = (TextView) rootView.findViewById(R.id.tv_average_hashrate_fragment_base);
                tvCurrentHashrate = (TextView) rootView.findViewById(R.id.tv_current_hashrate_fragment_base);
                tvReportedHashrate = (TextView) rootView.findViewById(R.id.tv_reported_hashrate_fragment_base);
                tvWorkers = (TextView) rootView.findViewById(R.id.tv_number_workers_fragment_base);
                lineChart = (LineChart) rootView.findViewById(R.id.graphEthermineHashrate);

                initBaseFragment();

                break;

            case FRAGMENT_WORKERS:
                rootView = inflater.inflate(R.layout.fragment_workers, container, false);
                spinner = (Spinner) rootView.findViewById(R.id.spiner_choice_of_wallet);
                TextView tv_namePool = (TextView) rootView.findViewById(R.id.tv_name_pool);

                final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_for_workers);
                final LinearLayoutManager verticalLayoutManager =
                        new LinearLayoutManager(rootView.getContext(),
                                LinearLayoutManager.VERTICAL,
                                false);
                recyclerView.setLayoutManager(verticalLayoutManager);
                adapterWorkers = new RecyclerAdatapterItems();

                recyclerView.setAdapter(adapterWorkers);

                List<String> dataForSpiner = new ArrayList<>();
                dataForSpiner.add(MyService.FIRST_WALLET_ETHERMINE);
                dataForSpiner.add(MyService.SECOND_WALLET_ETHERMINE);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataForSpiner);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        adapterWorkers.setWorkersEthermineToRecycler(
                                mPresenter.getWorkersEthermineFromResponse(dataForSpiner.get(position)));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
        }

        if (rootView == null)
            return defaultRootView;

        return rootView;
    }

    private void initBaseFragment() {
        // TODO Фрагмент не должен содержать последние ответы. Это должно происходить на стороне презентера.
        lineChart.setOnClickListener(v -> startActivity(new Intent(getContext(), GraphActivity.class)));

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        Log.d(LOG_TAG, "Now i will try to call refillFragmentBase()");
        refillFragmentBase();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activity = (PresenterStartActivity.IStartActivity) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void refillFragmentWorkers() {

//        if (responsesEthermine != null) {
//            List<WorkerEthermine> list = new ArrayList<>();
//            for (ResponseEthermine response : responsesEthermine)
//                list.addAll(response.getWorkers().getWorkersEthermine());
//            for (WorkerEthermine workerEthermine : list)
//                Log.d(LOG_TAG, "Name of worker before adding to adapterWorkers: " + workerEthermine.getWorker());
//            adapterWorkers.setWorkersEthermineToRecycler(list);
//            adapterWorkers.notifyDataSetChanged();
//        }
    }

    private void refillFragmentBase() {
        ConverterHashrate converter = ConverterHashrate.getInstance();

        Log.d(LOG_TAG, "refillFragmentBase() 1");
        String last_update = getResources().getString(R.string.last_update_text) + mPresenter.getTimeOfTheLastUpdate();

        String avgHashrate = converter.convertDoubleHashRateToString(mPresenter.getHashrateFromTheLastUpdates(PresenterStartActivity.AVG_HASHRATE));
        String currentHashrate = converter.convertDoubleHashRateToString(mPresenter.getHashrateFromTheLastUpdates(PresenterStartActivity.CURRENT_HASHRATE));
        String reportedHashrate = converter.convertDoubleHashRateToString(mPresenter.getHashrateFromTheLastUpdates(PresenterStartActivity.REPORTED_HASHRATE));

        setHashRates(avgHashrate, currentHashrate, reportedHashrate);

        tvLastUpdate.setText(last_update);
        tvWorkers.setText(mPresenter.getNumberOfWrokersFromTheLastUpdates());

        lineChart.setData(mPresenter.getDataforGraphInBaseFragment());
//        lineChart.invalidate();
    }

    private void setHashRates(String avgHashRate, String currentHashRate, String reportedHashRate) {
        Log.d(LOG_TAG, "setHashRates()");
        tvAverageHashrate.setText(avgHashRate);
        tvCurrentHashrate.setText(currentHashRate);
        tvReportedHashrate.setText(reportedHashRate);
    }

    @Override
    public void updateDataEthermine() {
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case FRAGMENT_BASE:
                refillFragmentBase();
                break;

            case FRAGMENT_WORKERS:
                refillFragmentWorkers();
                break;
        }
    }
}