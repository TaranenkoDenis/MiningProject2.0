package com.example.denis.miningproject20.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.views.presenters.GraphPresenter;
import com.example.denis.miningproject20.views.presenters.IGraphPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class GraphActivity extends AppCompatActivity {

    BarChart barChartEthermineShares;
    LineChart lineChartEthermineHashrate;
    LineChart lineChartEthermineWorkers;
    IGraphPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new GraphPresenter(this);

        lineChartEthermineHashrate = (LineChart) findViewById(R.id.lineChartEthermineHashrateDetail);
        barChartEthermineShares = (BarChart) findViewById(R.id.barChartEthermineSharesDetail);
        lineChartEthermineWorkers = (LineChart) findViewById(R.id.lineChartEthermineWorkersDetail);


        lineChartEthermineHashrate.setData(mPresenter.getAvgHashrateForGraphHashrates());
        lineChartEthermineHashrate.setData(mPresenter.getCurrentHashrateForGraphHashrates());
        lineChartEthermineHashrate.setData(mPresenter.getReportedHashrateForGraphHashrates());

        barChartEthermineShares.setData(mPresenter.getStaleSharesForGraphShares());
        barChartEthermineShares.setData(mPresenter.getInvalidSharesForGraphShares());
        barChartEthermineShares.setData(mPresenter.getValidSharesForGraphShares());

        lineChartEthermineWorkers.setData(mPresenter.getDataForGraphWorkers());
    }
}
