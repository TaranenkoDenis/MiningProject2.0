package com.example.denis.miningproject20.views;

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
import android.util.Log;

import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.service.MyService;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by denis on 20.07.17.
 */

public class MainPresenter {

    private static final String LOG_TAG = "MY_LOG: " + MainPresenter.class.getSimpleName();
    private WeakReference<IMainActivityView> mWeakView;

    private boolean bound = false;
    private Messenger mService;
    private Messenger mMessenger = new Messenger(new IncomingMessengerFromService(this));

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

    private class IncomingMessengerFromService extends Handler {

        private final WeakReference<MainPresenter> mainPresenter;

        private IncomingMessengerFromService(MainPresenter mainPresenter) {
            this.mainPresenter = new WeakReference<>(mainPresenter);
        }

        @Override
        public void handleMessage(Message msg) {

            Log.d(LOG_TAG, "I have some message.");

            switch (msg.what){
                case MyService.COMMAND_MESSAGE_TO_UI:

                    Log.d(LOG_TAG, "It's massage for UI.");

                    Bundle bundle = msg.getData();
                    Log.d(LOG_TAG, "" + bundle.getSerializable(MyService.KEY_LAST_RESPONSE_ETHERMINE));
                    if(bundle.getString(MyService.KEY_LAST_RESPONSE_ETHERMINE) != null){

                        Log.d(LOG_TAG, "Test. DatabaseHelper instance = " + DatabaseHelper.getInstance());
                        Log.d(LOG_TAG, "This message is lastResponsesEthermine. Number of last responses from ethermine: "
                                + DatabaseHelper.getInstance().getLastResponsesEthermine().size());

                        DatabaseHelper.getInstance().getLastResponsesEthermine().get(0).checkResponse();
                        DatabaseHelper.getInstance().getLastResponsesEthermine().get(1).checkResponse();

                        List<ResponseEthermine> lastResponsesEthermine = DatabaseHelper.getInstance().getLastResponsesEthermine();

                        lastResponsesEthermine.get(0).checkResponse();

//                        mainPresenter.get().mWeakView.get().setHashRates(
//                                lastResponsesEthermine.get(0).getHashRate(),
//                                convertDoubleHashRateToString(lastResponsesEthermine.get(0).getAvgHashrate()),
//                                lastResponsesEthermine.get(0).getReportedHashRate());
//
//
//                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
//                        String lastTimeUpdate = "Last update: " + sdf.format(lastResponsesEthermine.get(0).getDate());
//
//                        mainPresenter.get().mWeakView.get().setLastUpdateTime(lastTimeUpdate);
//
//                        mainPresenter.get().mWeakView.get().setNumberOfWorkers(
//                                lastResponsesEthermine.get(0).getWorkers().getWorkersEthermine().size(),
//                                lastResponsesEthermine.get(0).getWorkers().getWorkersEthermine().size());

                    }
                    break;
            }
        }
    }

    public MainPresenter(IMainActivityView mainActivity) {
        this.mWeakView = new WeakReference<>(mainActivity);

        Intent intentService = new Intent(mWeakView.get().getContext(), MyService.class);
        Log.d(LOG_TAG, "Is service was started? - " +
                mWeakView.get().getContext().startService(intentService));
        Log.d(LOG_TAG, "Are binding was successful? " +
                mWeakView.get().getContext().bindService(intentService, serviceConnection, BIND_AUTO_CREATE));

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
        if(tera <= number)
            result = new BigDecimal(number / tera).setScale(1, RoundingMode.UP).doubleValue() + " TH/s";
        return result;
    }

    private void immediateUpdateData() {
        try {
            Message msg = Message.obtain(null, MyService.COMMAND_IMMEDIATE_UPDATE);
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unBindService() {
        if(!bound) return;
        mWeakView.get().getContext().unbindService(serviceConnection);
        bound = false;
    }

    public interface IMainActivityView {
        void setNumberOfWorkers(long currentNumber, long generalNumber);
        void setLastUpdateTime(String timeOfTheLastUpdate);
        void setHashRates(String hashRate, String avgHashRate, String reportedHashRate);
        Context getContext();
    }
}
