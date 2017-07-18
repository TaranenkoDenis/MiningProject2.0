package com.example.denis.miningproject20.views;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;
import com.example.denis.miningproject20.presenters.IMainPresenter;
import com.example.denis.miningproject20.service.MyService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ActivityMain extends AppCompatActivity implements IMainView {

    private final static String LOG_TAG = "MY_LOG: " + ActivityMain.class.getSimpleName();

    private IMainPresenter presenter;

    private TextView tvLastUpdate;
    private TextView tvAverageHashrate;
    private TextView tvCurrentHashrate;
    private TextView tvReportedHashrate;
    private TextView tvWorkers;

    private ServiceConnection serviceConnection;
    private boolean bound = false;
    private Messenger mService = null;
    private Messenger mMessenger = new Messenger(new IncomingMessenger());
    private ResponseEthermine lastResponseEthermine;

    class IncomingMessenger extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyService.COMMAND_MESSAGE_TO_UI:
                    Log.d(LOG_TAG, "COMMAND_MESSAGE_TO_UI");
                    Bundle bundle = msg.getData();

                    Log.d(LOG_TAG, "MyService.KEY_LAST_RESPONSE_ETHERMINE = " + MyService.KEY_LAST_RESPONSE_ETHERMINE);
//                    if(bundle.getString(MyService.KEY_LAST_RESPONSE_ETHERMINE) != null){
//                        ResponseEthermine lastResponseEthermine = MyService.RepositoryViews.getInstance().getLastResponseEthermine();
//                        Log.d(LOG_TAG, "lastResponseEthermine = " + lastResponseEthermine);
//                        setHashRates(lastResponseEthermine.getHashRate(),
//                                convertAvgHashRate(lastResponseEthermine.getAvgHashrate()),
//                                lastResponseEthermine.getReportedHashRate());
//                    }
                    if(bundle.getString(MyService.KEY_ERROR) == null
                            && bundle.getSerializable(MyService.KEY_LAST_RESPONSE_ETHERMINE) != null){

                        Log.d(LOG_TAG, "We in my important case! ");

                        lastResponseEthermine =
                                (ResponseEthermine) bundle.getSerializable(MyService.KEY_LAST_RESPONSE_ETHERMINE);

                        RepositoryViews.getInstance().setLastResponseEthermine(lastResponseEthermine);

                        setHashRates(lastResponseEthermine.getHashRate(),
                                convertAvgHashRate(lastResponseEthermine.getAvgHashrate()),
                                lastResponseEthermine.getReportedHashRate());
                        setWorkers(lastResponseEthermine.getWorkers().getWorkersEthermine().size(),
                                lastResponseEthermine.getWorkers().getWorkersEthermine().size());
                    }
                    break;
            }
        }
    }

    public void registerActivityInService(View v){
        if(!bound) return;

        Message msg = Message.obtain(null, MyService.COMMAND_REGISTER_CLIENT);
        msg.replyTo = mMessenger;

        Bundle bundle = new Bundle();
        ResponseEthermine responseEthermine = new ResponseEthermine();
        responseEthermine.setAddress("dwdfwewefvwevfwf");
        bundle.putSerializable("test", responseEthermine);
        msg.setData(bundle);

        try {
            Log.d(LOG_TAG, "Registration of client");
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentService = new Intent(this, MyService.class);

        tvAverageHashrate = (TextView) findViewById(R.id.tv_average_hashrate);
        tvCurrentHashrate = (TextView) findViewById(R.id.tv_current_hashrate);
        tvReportedHashrate = (TextView) findViewById(R.id.tv_reported_hashrate);
        tvWorkers = (TextView) findViewById(R.id.tv_number_workers);
        tvLastUpdate = (TextView) findViewById(R.id.tv_last_update);

        Log.d(LOG_TAG, "ServiceConnection was created.");
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "onServiceConnected");
                mService = new Messenger(service);
                bound = true;
                registerActivityInService(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "onServiceDisconnected");
                mService = null;
                bound = false;
            }
        };

        Log.d(LOG_TAG, "startService(intentService) "
                + startService(intentService));
        Log.d(LOG_TAG, "Binding was successful? " + bindService(intentService, serviceConnection, BIND_AUTO_CREATE));

        presenter = new MainPresenter(this);
        presenter.getEthermineData(false);

        OnClickListenerValueHashrate clickListenerValueHashrate = new OnClickListenerValueHashrate();

        tvWorkers.setOnClickListener( v -> {
            Intent intent = new Intent(this, ActivityWorkers.class);
            intent.putExtra(RepositoryViews.class.getSimpleName(), true);
            startActivity(intent);
        });

        tvAverageHashrate.setOnClickListener(clickListenerValueHashrate);
        tvCurrentHashrate.setOnClickListener(clickListenerValueHashrate);
        tvReportedHashrate.setOnClickListener(clickListenerValueHashrate);

//        getDwarfPoll();
//        getEthermine();

        registerActivityInService(null);

        // Example of using GraphView TODO change this
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graphView.addSeries(series);
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
                startActivity(new Intent(this, ActivitySettings.class));
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
    public void setCurrency() {

    }

    @Override
    public void setWorkers(long currentNumber, long generalNumber) {
        tvWorkers.setText(currentNumber + "/" + generalNumber);
    }

    @Override
    public void setResponse() {
    }

    private class OnClickListenerValueHashrate implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.tv_average_hashrate:
                    getNotification();
                    break;

                case R.id.tv_current_hashrate:

                    break;

                case R.id.tv_reported_hashrate:

                    break;
            }
        }
    }

    public void getNotification(){

        ResponseEthermine response = null;

        Log.d(LOG_TAG, "I have notification.");

        Log.d(LOG_TAG, "before getLastResponseEthermine()");

        if(response != null){

            long numberOfAliveWorkers = getNumberOfAliveWorkers(response.getWorkers().getWorkersEthermine());
            tvWorkers.setText(numberOfAliveWorkers + "/" + response.getWorkers().getWorkersEthermine().size());

            tvLastUpdate.setText(new SimpleDateFormat("h:mm:ss", Locale.getDefault())
                    .format(response.getTimeOfTheLastUpdate()));

            tvCurrentHashrate.setText(response.getHashRate());
            tvAverageHashrate.setText(convertAvgHashRate(response.getAvgHashrate()));
            tvReportedHashrate.setText(response.getReportedHashRate());
        }
    }

    private long getNumberOfAliveWorkers(List<WorkerEthermine> workersEthermine) {
        long result = 0;
        for(WorkerEthermine worker : workersEthermine)
            if(worker.getInvalidShares() == 0)
                ++result;
        return result;
    }

    private String convertAvgHashRate(Double number) {
        String result = "";

        Double kilo = Math.pow(10, 3), mega = Math.pow(10, 6),
                giga = Math.pow(10, 9), tera = Math.pow(10, 12);

        if(kilo <= number && number < mega)
            result = new BigDecimal(number / kilo).setScale(1, RoundingMode.UP).doubleValue() + " KH/s";
        if(mega <= number && number < giga)
            result = new BigDecimal(number / mega).setScale(1, RoundingMode.UP).doubleValue() + " MH/s";
        if(giga <= number && number < tera)
            result = new BigDecimal(number / giga).setScale(1, RoundingMode.UP).doubleValue() + " GH/s";
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onClickUnBind(null);
    }

    private void onClickUnBind(View v) {
        if(!bound) return;
        unbindService(serviceConnection);
        bound = false;
    }
}
