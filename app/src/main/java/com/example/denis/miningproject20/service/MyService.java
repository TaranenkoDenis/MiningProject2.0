package com.example.denis.miningproject20.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.dwarfpool.ResponseDwarfpool;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private final static String LOG_TAG = "MY_LOG: " + MyService.class.getSimpleName();

    public final static String FIRST_WALLET_ETHERMINE = "8f7ae5c3883f1079a8c3280ef97aabdd5e9a7960";
    public final static String SECOND_WALLET_ETHERMINE = "de088812a9c5005b0dc8447b37193c9e8b67a1ff";

    public static final int ID_ETHERMINE = 1;
    public static final int ID_DWARFPOOL = 2;

    public static final int COMMAND_IMMEDIATE_UPDATE = 3;
    public static final int COMMAND_REGISTER_CLIENT = 4;
    public static final int COMMAND_UNREGISTER_CLIENT = 5;
    public static final int COMMAND_MESSAGE_TO_UI = 6;
    public static final int COMMAND_CHECK_DB = 7;

    public static final String KEY_LAST_RESPONSE_ETHERMINE = "last_response_ethermine";
    public static final String KEY_LAST_RESPONSE_DWARFPOOL = "last_response_dwarfpool";
    public static final String KEY_ERROR = "error from service";

    static final String FLAG_REQUESTS_TO_ETHERMINE_COMPLITED = "Ethermine";
    static final String FLAG_REQUESTS_TO_DWARFPOOL_COMPLITED = "Dwarfpool";

    public static final int NOTIFICATION_ID = 5748;

    DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private int numberOfNotifications = 1;

    List<ResponseEthermine> lastResponsesEthermine;
    List<ResponseDwarfpool> lastResponsesDwarfpool;

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private SharedPreferences sharedPref;

    private ScheduledExecutorService execSerivce;

    private List<MyRunnerRequests> listRunners;

    private List<Messenger> mClients = new ArrayList<>();
    private final Messenger mMessenger = new Messenger(new IncomingServiceHandler(this));

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");

//        sharedPreferenceChangeListener = (sharedPreferences, key) -> {
//            if(key.equals(SettingsActivity.KEY_PREF_EMAIL))
//                Log.d(LOG_TAG, "I have notice that email was changed");
//        };

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        listRunners = new LinkedList<>();
        listRunners.add(new MyRunnerRequests(ID_ETHERMINE, this));
        listRunners.add(new MyRunnerRequests(ID_DWARFPOOL, this));

        lastResponsesDwarfpool = new LinkedList<>();
        lastResponsesEthermine = new LinkedList<>();

        execSerivce = Executors.newScheduledThreadPool(listRunners.size()); // number of pools (ethermine, dwarfpool)

        Log.d(LOG_TAG, "Service was created.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        Log.d(LOG_TAG, "Service was destroyed.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(LOG_TAG, "onStartCommand");

        if((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск сервиса
            Log.d(LOG_TAG, "Service start working in first time or with START_FLAG_RETRY");
        } else {
            // TODO Перезапуск по другой причине
            Log.d(LOG_TAG, "Service start working with different flag.");
        }

        Log.d(LOG_TAG, "Service was started. Test of Database:");
        for(ResponseEthermine response : DatabaseHelper.getInstance().getLastResponsesEthermine()){
            response.checkResponse();
        }
        startWorking();
        return Service.START_STICKY;
    }

    private void startWorking() {
        for(MyRunnerRequests request : listRunners)
            execSerivce.scheduleWithFixedDelay(request, 0, 3, TimeUnit.MINUTES);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return mMessenger.getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private class IncomingServiceHandler extends Handler {

        private final WeakReference<MyService> mServiceWR;

        private IncomingServiceHandler(MyService mServiceWR) {
            this.mServiceWR = new WeakReference<>(mServiceWR);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case COMMAND_IMMEDIATE_UPDATE:
                    Log.d(LOG_TAG, "Activity wants to update date.");

                case COMMAND_REGISTER_CLIENT:
                    Log.d(LOG_TAG, "Is the client registered? - "
                            + mServiceWR.get().mClients.add(msg.replyTo));
                    break;

                case COMMAND_UNREGISTER_CLIENT:
                    Log.d(LOG_TAG, "Is the client unregistered? - "
                            + mServiceWR.get().mClients.remove(msg.replyTo));
                    break;
                case COMMAND_CHECK_DB:
                    Log.d(LOG_TAG, "Request to check DB.");
                    testDB();
                    startWorking();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void testDB(){
        Log.d(LOG_TAG, "Test. DatabaseHelper instance = " + databaseHelper);
        List<ResponseEthermine> list = databaseHelper.getLastResponsesEthermine();
        for(ResponseEthermine response : list)
            response.checkResponse();
    }

    public void sendResponseFromPoolToUI(String responseFromRunner){

        Log.d(LOG_TAG, "Size of responsesEthermine = " + lastResponsesEthermine.size());
        Log.d(LOG_TAG, "I put responsesEthermine to db. Test:");

        for(ResponseEthermine response : lastResponsesEthermine) {
            response.checkResponse();
            databaseHelper.putResponseEthermineToDatabase(response);
        }

        Log.d(LOG_TAG, "Test. After adding to database. List of the responses from database: ");
        testDB();

        for(int i = mClients.size()-1; i>=0; i--){

            try {
                Bundle bundle = new Bundle();
                Message msg = Message.obtain(null, COMMAND_MESSAGE_TO_UI);

                    if(responseFromRunner.equals(FLAG_REQUESTS_TO_ETHERMINE_COMPLITED)){
                        Log.d(LOG_TAG, "I send notification to ui. Response from ethermine");
                        // TODO change this
                        bundle.putString(KEY_LAST_RESPONSE_ETHERMINE, "Success");
                    }
                    if(responseFromRunner.equals(FLAG_REQUESTS_TO_DWARFPOOL_COMPLITED)){
                        // TODO change this
                        bundle.putSerializable(KEY_LAST_RESPONSE_DWARFPOOL, (Serializable) lastResponsesDwarfpool);
                    }

                msg.setData(bundle);

                mClients.get(i).send(msg);

            } catch (RemoteException e) {
                mClients.remove(i); // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
                e.printStackTrace();
            }
        }
    }
}
