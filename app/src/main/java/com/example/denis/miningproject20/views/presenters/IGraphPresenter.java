package com.example.denis.miningproject20.views.presenters;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by denis on 23.08.17.
 */

public interface IGraphPresenter {
    LineData getDataForGraphWorkers();
    LineData getDataForGraphHashrate();
    BarData getDataForBarGraphShares();
}
