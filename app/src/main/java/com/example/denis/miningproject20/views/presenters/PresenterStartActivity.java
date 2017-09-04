package com.example.denis.miningproject20.views.presenters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;
import com.example.denis.miningproject20.service.MyService;
import com.example.denis.miningproject20.views.ConverterHashrate;
import com.example.denis.miningproject20.views.activities.StartActivity;
import com.example.denis.miningproject20.views.fragments.IListenerUpdatesFromServers;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by denis on 30.07.17.
 */

public class PresenterStartActivity {
    private static final String LOG_TAG = "MY_LOG: " + PresenterStartActivity.class.getSimpleName();
    public static final int AVG_HASHRATE = 1;
    public static final int CURRENT_HASHRATE = 2;
    public static final int REPORTED_HASHRATE = 3;
    private WeakReference<StartActivity> mWeakActivity;

    private boolean bound = false;
    private Messenger mService;
    private Messenger mMessenger = new Messenger(new PresenterStartActivity.IncomingMessengerFromService(this));

    private static PresenterStartActivity currentInstance;

    private List<ResponseEthermine> lastResponsesEthermine;

    private static final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
    private static final ConverterHashrate converter = ConverterHashrate.getInstance();

    private IListenerUpdatesFromServers listenersUpdate;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(LOG_TAG, "onServiceConnected");
            mService = new Messenger(service);
            bound = true;

            Message msg = Message.obtain(null, MyService.COMMAND_REGISTER_CLIENT);
            msg.replyTo = mMessenger;

            try {
                Log.d(LOG_TAG, "Registration of client");
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(LOG_TAG, "Service disconnected.");
        }
    };

    private PresenterStartActivity(StartActivity startActivity) {
        this.mWeakActivity = new WeakReference<StartActivity>(startActivity);

        this.lastResponsesEthermine = databaseHelper.getLastResponsesEthermine();

        Intent intentService = new Intent(mWeakActivity.get().getContext(), MyService.class);
        Log.d(LOG_TAG, "Is service was started? - " +
                mWeakActivity.get().getContext().startService(intentService));
        Log.d(LOG_TAG, "Are binding was successful? " +
                mWeakActivity.get().getContext().bindService(intentService, serviceConnection, BIND_AUTO_CREATE));
    }

    public synchronized void registerListenerUpdate(IListenerUpdatesFromServers listener){
        listenersUpdate = listener;
    }

    public synchronized void unregisterListenerUpdate(){
        listenersUpdate = null;
    }

    public void immediateUpdateData() {
        try {
            Message msg = Message.obtain(null, MyService.COMMAND_IMMEDIATE_UPDATE);
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unBindService() {
        if(!bound) return;
        mWeakActivity.get().getContext().unbindService(serviceConnection);
        bound = false;
    }

    public static PresenterStartActivity createInstance(StartActivity activity) {

        if(currentInstance == null)
            currentInstance = new PresenterStartActivity(activity);

        return currentInstance;
    }

    public static PresenterStartActivity getCurrentInstance(){
        if(currentInstance == null)
            throw new RuntimeException();
        return currentInstance;
    }

    public void checkDB() {
        List<ResponseEthermine> listOfLastResponsesEthermine
                = DatabaseHelper.getInstance().getLastResponsesEthermine();

        Log.d(LOG_TAG, "Test. Instance of database = " + DatabaseHelper.getInstance()
                + "\nPath of database = " + mWeakActivity.get().getDatabasePath(DatabaseHelper.getInstance().getDatabaseName()));


        Log.d(LOG_TAG, "Test of responses from db in PresenterStartActivity: ");
        for (ResponseEthermine response : listOfLastResponsesEthermine)
            response.checkResponse();

        try {
            Message msg = Message.obtain(null, MyService.COMMAND_CHECK_DB);
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public LineData getDataforGraphInBaseFragment() {

        List<ResponseEthermine> allResponses = databaseHelper.getAllResponsesEthermine();
        List<Entry> series = new ArrayList<>();

        // TODO Спросить у Егора как будет строиться этот базовый график ?
        // Просто суммировать хэшрейты на одинаковом отрезке времени, а потом разделить на кол-во кошельков ?
        // Или опять сделать спинер для выбора кошелька ?

        LineDataSet dataSet = new LineDataSet(series, "Hashrate");
//        dataSet.setColor(mWeakActivity.get().getResources().getColor(R.color.colorCurrentHashrate));
        LineData lineData = new LineData();

//        return new LineData(dataSet);
        return lineData;
    }

    @Nullable
    public String getTimeOfTheLastUpdate() {
        List<Long> timeOfTheLastUpdates = new ArrayList<>();
        if(lastResponsesEthermine.size() > 0) {
            for (ResponseEthermine response : lastResponsesEthermine)
                timeOfTheLastUpdates.add(response.getDate());

            Long lastUpdate = findMax(timeOfTheLastUpdates);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd MMM yyyy", Locale.getDefault());

            if (lastUpdate != null)
                return sdf.format(new Date(lastUpdate));
        }
        return "";
    }

    @Nullable
    private Long findMax(List<Long> list) {
        if(list.size() > 0) {
            Long max = list.get(0);
            for (Long l : list)
                if (max < l) max = l;
            return max;
        }
        return null;
    }

    /**
     * <p>Returns a string which containing a value for a textView in StartActivity.</p>
     * <p>If parameter = PresenterStartActivity.AVG_HASHRATE, method returns avg hashrate.</p>
     * <p>If parameter = PresenterStartActivity.CURRENT_HASHRATE, method returns current hashrate.</p>
     * <p>If parameter = PresenterStartActivity.REPORTED_HASHRATE, method returns reported hashrate.</p>
     * @param flag - говорит о том, какой хэшрейт нужно вренуть.
     * @return
     */
    @Nullable
    public Double getHashrateFromTheLastUpdates(int flag) {
        Double result = 0d;
        ConverterHashrate converter = ConverterHashrate.getInstance();

        if(lastResponsesEthermine.size() <= 0)
            return null;

        for(ResponseEthermine response : lastResponsesEthermine)
            switch (flag) {
                case AVG_HASHRATE:
                    result += response.getAvgHashrate();
                    break;
                case CURRENT_HASHRATE:
                    result += converter.convertStringHashRateToDouble(response.getHashRate());
                    break;
                case REPORTED_HASHRATE:
                    result += converter.convertStringHashRateToDouble(response.getReportedHashRate());
                    break;
            }
        return result;
    }

    public String getNumberOfWrokersFromTheLastUpdates() {

        long totalNumberOfWorkers = 0L;

        for (ResponseEthermine response : lastResponsesEthermine)
            totalNumberOfWorkers += response.getWorkers().getWorkersEthermine().size();

        // TODO как понять, что рабочий упал ?
        return totalNumberOfWorkers + "/" + totalNumberOfWorkers;
    }

    public List<WorkerEthermine> getWorkersEthermineFromResponse(String wallet) {
        ResponseEthermine response = databaseHelper.getLastResponseEthermine(wallet);
        return response.getWorkers().getWorkersEthermine();
    }

    public interface IStartActivity {
        Context getContext();
        PresenterStartActivity getPresenter();
    }

    private class IncomingMessengerFromService extends Handler {

        private final WeakReference<PresenterStartActivity> startActivityPresenterRef;

        private IncomingMessengerFromService(PresenterStartActivity startPresenter) {
            this.startActivityPresenterRef = new WeakReference<>(startPresenter);
        }

        @Override
        public void handleMessage(Message msg) {

            Log.d(LOG_TAG, "I have message.");

            switch (msg.what) {
                case MyService.COMMAND_MESSAGE_TO_UI:

                    Log.d(LOG_TAG, "It's massage for UI.");

                    Bundle bundle = msg.getData();
                    Log.d(LOG_TAG, "" + bundle.getSerializable(MyService.KEY_LAST_RESPONSE_ETHERMINE));

                    if (bundle.getString(MyService.KEY_LAST_RESPONSE_ETHERMINE) != null) {

                        List<ResponseEthermine> listOfLastResponsesEthermine
                                = DatabaseHelper.getInstance().getLastResponsesEthermine();

                        Log.d(LOG_TAG, "Test of responses from db in PresenterStartActivity: listOfLastResponsesEthermine = "
                                + listOfLastResponsesEthermine);

                        if(listOfLastResponsesEthermine != null) {
                            Log.d(LOG_TAG, "Test of responses from db in PresenterStartActivity: ");
                            for (ResponseEthermine response : listOfLastResponsesEthermine)
                                response.checkResponse();
                        }

                        if(listenersUpdate != null)
                            listenersUpdate.updateDataEthermine();
                    }
                    break;
            }
        }
    }
}
