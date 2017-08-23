package com.example.denis.miningproject20.views.presenters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.Repository;
import com.example.denis.miningproject20.views.GraphActivity;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 21.08.17.
 */

public class GraphPresenter implements IGraphPresenter{
    private WeakReference<GraphActivity> graphActivityWR;
    private static final String LOG_TAG = "MY_LOG: " + GraphPresenter.class.getSimpleName();

    public GraphPresenter(GraphActivity activity) {
        this.graphActivityWR = new WeakReference<GraphActivity>(activity);
    }

    @Override
    public LineData getAvgHashrateForGraphHashrates() {
        List<Entry> series = new ArrayList<>();
        series.add(new Entry(0, 1));
        series.add(new Entry(1, 5));
        series.add(new Entry(2, 3));
        series.add(new Entry(3, 2));
        series.add(new Entry(4, 6));
        LineDataSet dataSet = new LineDataSet(series, "Average hashrate");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorAverageHashrate));

        return new LineData(dataSet);
    }

    @Override
    public LineData getCurrentHashrateForGraphHashrates() {
        List<Entry> series = new ArrayList<>();
        series.add(new Entry(0, 2));
        series.add(new Entry(2, 6));
        series.add(new Entry(3, 4));
        series.add(new Entry(4, 3));
        series.add(new Entry(5, 7));
        LineDataSet dataSet = new LineDataSet(series, "Current hashrate");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorCurrentHashrate));

        return new LineData(dataSet);
    }

    @Override
    public LineData getReportedHashrateForGraphHashrates() {
        List<Entry> series = new ArrayList<>();
        series.add(new Entry(0, 3));
        series.add(new Entry(3, 7));
        series.add(new Entry(4, 5));
        series.add(new Entry(5, 4));
        series.add(new Entry(6, 8));
        LineDataSet dataSet = new LineDataSet(series, "Reported hashrate");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorReportedHashrate));

        return new LineData(dataSet);
    }

    @Override
    public BarData getValidSharesForGraphShares() {
        List<BarEntry> series = new ArrayList<>();
        series.add(new BarEntry(0, 3));
        series.add(new BarEntry(3, 7));
        series.add(new BarEntry(4, 5));
        series.add(new BarEntry(5, 4));
        series.add(new BarEntry(6, 8));
        BarDataSet dataSet = new BarDataSet(series, "Valid shares");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorValidShares));

        return new BarData(dataSet);
    }

    @Override
    public BarData getInvalidSharesForGraphShares() {
        List<BarEntry> series = new ArrayList<>();
        series.add(new BarEntry(0, 2));
        series.add(new BarEntry(2, 6));
        series.add(new BarEntry(3, 4));
        series.add(new BarEntry(4, 3));
        series.add(new BarEntry(5, 7));
        BarDataSet dataSet = new BarDataSet(series, "Invalid shares");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorInvalidShares));

        return new BarData(dataSet);
    }

    @Override
    public BarData getStaleSharesForGraphShares() {
        List<BarEntry> series = new ArrayList<>();
        series.add(new BarEntry(0, 1));
        series.add(new BarEntry(1, 5));
        series.add(new BarEntry(2, 3));
        series.add(new BarEntry(3, 2));
        series.add(new BarEntry(4, 6));
        BarDataSet dataSet = new BarDataSet(series, "Stale shares");
        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorStaleShares));

        return new BarData(dataSet);
    }

    @Override
    public LineData getDataForGraphWorkers() {

        Map<Long, Integer> liveWorkers = Repository.getNumberAliveWorkersEthermine();

        Log.d(LOG_TAG, "Test Repository.getNumberAliveWorkersEthermine()");
        for(Map.Entry liveWorker : liveWorkers.entrySet())
            Log.d(LOG_TAG, "key = " + liveWorker.getKey() + ", value = " + liveWorker.getValue());

        List<Entry> entries = new ArrayList<>();

//        for(Map.Entry liveWorker : liveWorkers.entrySet()){
//            entries.add(new Entry((Long)liveWorker.getKey(), (Integer)liveWorker.getValue()));
//        }

//        LineDataSet dataSet = new LineDataSet(entries, "Active workers");
//        dataSet.setColor(graphActivityWR.get().getResources().getColor(R.color.colorWorkers));

//        return new LineData(dataSet);
        return null;
    }
}