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
import android.util.Log;

import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.service.MyService;
import com.example.denis.miningproject20.views.StartActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by denis on 30.07.17.
 */

public class PresenterStartActivity {
    private static final String LOG_TAG = "MY_LOG: " + PresenterStartActivity.class.getSimpleName();
    private WeakReference<StartActivity> mWeakActivity;

    private boolean bound = false;
    private Messenger mService;
    private Messenger mMessenger = new Messenger(new PresenterStartActivity.IncomingMessengerFromService(this));

    private static PresenterStartActivity currentInstance;

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

        Intent intentService = new Intent(mWeakActivity.get().getContext(), MyService.class);
        Log.d(LOG_TAG, "Is service was started? - " +
                mWeakActivity.get().getContext().startService(intentService));
        Log.d(LOG_TAG, "Are binding was successful? " +
                mWeakActivity.get().getContext().bindService(intentService, serviceConnection, BIND_AUTO_CREATE));
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

                        startActivityPresenterRef.get()
                                .mWeakActivity.get().setlastResponsesEthermine(listOfLastResponsesEthermine);
                    }
                break;
            }
        }
    }

    public interface IStartActivity {

        // Base Fragment
        void setLastResponses(List<ResponseEthermine> listOfResponses);
        void setCurrency();
        void setDataForGraphic();
        Context getContext();

        // Graphics fragment

        // Workers fragment
        void setNumberOfWorkersInBaseFragment();
    }
}
