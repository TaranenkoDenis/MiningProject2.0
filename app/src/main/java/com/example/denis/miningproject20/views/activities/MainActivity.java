package com.example.denis.miningproject20.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.views.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.IMainActivityView {

    private final static String LOG_TAG = "MY_LOG: " + MainActivity.class.getSimpleName();

    private TextView tvLastUpdate;
    private TextView tvAverageHashrate;
    private TextView tvCurrentHashrate;
    private TextView tvReportedHashrate;
    private TextView tvWorkers;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAverageHashrate = (TextView) findViewById(R.id.tv_average_hashrate);
        tvCurrentHashrate = (TextView) findViewById(R.id.tv_current_hashrate);
        tvReportedHashrate = (TextView) findViewById(R.id.tv_reported_hashrate);
        tvWorkers = (TextView) findViewById(R.id.tv_number_workers);
        tvLastUpdate = (TextView) findViewById(R.id.tv_last_update);

        tvWorkers.setOnClickListener(v -> {
            this.startActivity(new Intent(this, WorkersActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_update_data:
                break;

            case R.id.action_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    public void setHashRates(String hashRate, String avgHashRate, String reportedHashRate) {
        tvCurrentHashrate.setText(hashRate);
        tvAverageHashrate.setText(avgHashRate);
        tvReportedHashrate.setText(reportedHashRate);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setNumberOfWorkers(long currentNumber, long generalNumber) {
        tvWorkers.setText(currentNumber + "/" + generalNumber);
    }

    @Override
    public void setLastUpdateTime(String timeOfTheLastUpdate) {
        tvLastUpdate.setText(timeOfTheLastUpdate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unBindService();
    }
}
