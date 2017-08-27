package com.example.denis.miningproject20.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.service.MyService;
import com.example.denis.miningproject20.views.presenters.GraphPresenter;
import com.example.denis.miningproject20.views.presenters.IGraphPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {

    private BarChart barChartEthermineShares;
    private LineChart lineChartEthermineHashrate;
    private LineChart lineChartEthermineWorkers;
    private IGraphPresenter mPresenter;
    private Spinner spinner;
    private TextView tvTitlePool;

    List<String> dataForSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new GraphPresenter(this);

        dataForSpiner = new ArrayList<>();
        dataForSpiner.add(MyService.FIRST_WALLET_ETHERMINE);
        dataForSpiner.add(MyService.SECOND_WALLET_ETHERMINE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.spiner_choice_of_wallet);
        tvTitlePool = (TextView) findViewById(R.id.tv_title_of_pool);
        lineChartEthermineHashrate = (LineChart) findViewById(R.id.lineChartEthermineHashrateDetail);
        barChartEthermineShares = (BarChart) findViewById(R.id.barChartEthermineSharesDetail);
        lineChartEthermineWorkers = (LineChart) findViewById(R.id.lineChartEthermineWorkersDetail);

        init();
    }

    private void init() {

        // Graphics
        lineChartEthermineHashrate.setData(mPresenter.getDataForGraphHashrate());
        barChartEthermineShares.setData(mPresenter.getDataForBarGraphShares());
        lineChartEthermineWorkers.setData(mPresenter.getDataForGraphWorkers());

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataForSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GraphActivity.this, "Ваш выбор: " + dataForSpiner.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
