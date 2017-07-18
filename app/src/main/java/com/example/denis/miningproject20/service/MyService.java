package com.example.denis.miningproject20.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.denis.miningproject20.MyMessageEvent;
import com.example.denis.miningproject20.R;
import com.example.denis.miningproject20.models.IResponse;
import com.example.denis.miningproject20.models.dwarfpool.ResponseDwarfpool;
import com.example.denis.miningproject20.network.ApiEthermine;
import com.example.denis.miningproject20.network.IEthermineAPI;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.views.ActivityMain;
import com.example.denis.miningproject20.views.ActivitySettings;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {

    private final static String LOG_TAG = "MY_LOG: " + MyService.class.getSimpleName();

    private final int ID_ETHERMINE = 1;
    private final int ID_DWARFPOOL = 2;
    public static final int COMMAND_IMMEDIATE_UPDATE_ID = 1;
    public static final int COMMAND_REGISTER_CLIENT = 2;
    public static final int COMMAND_UNREGISTER_CLIENT = 3;
    public static final int COMMAND_MESSAGE_TO_UI = 4;

    public static final String KEY_LAST_RESPONSE_ETHERMINE = "last_response_ethermine";
    public static final String KEY_ERROR = "error from service";
    public static final int NOTIFICATION_ID = 5748;

    private int numberOfNotifications = 1;

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    private ScheduledExecutorService execSerivce;

    private List<MyRunnerRequests> listRunners;

    private List<Messenger> mClients = new ArrayList<>();
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate()");

        sharedPreferenceChangeListener = (sharedPreferences, key) -> {
            if(key.equals(ActivitySettings.KEY_PREF_EMAIL))
                Log.d(LOG_TAG, "I have notice that email was changed");
        };

        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        listRunners = new LinkedList<>();
        listRunners.add(new MyRunnerRequests(ID_ETHERMINE));
        listRunners.add(new MyRunnerRequests(ID_DWARFPOOL));

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


        for(MyRunnerRequests request : listRunners)
            execSerivce.scheduleWithFixedDelay(request, 0, 30, TimeUnit.SECONDS);

        return Service.START_STICKY;
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

    private class MyRunnerRequests implements Runnable{

        private int id_pool;

        private MyRunnerRequests(int id_pool) {
            this.id_pool = id_pool;

        }

        @Override
        public void run() {
            switch (id_pool){
                case ID_DWARFPOOL:
                    Log.d(LOG_TAG, "MyRun -> request to Dwarfpool");
                    requestToDwarfpool();
                    break;

                case ID_ETHERMINE:
                    Log.d(LOG_TAG, "MyRun -> request to Ethermine");
                    requestToEthermine();
                    break;
            }
        }

        private void requestToDwarfpool() {
            Log.d(LOG_TAG, "requestToDwarfpool()");
        }

        private void requestToEthermine() {
            boolean result;
            Log.d(LOG_TAG, "requestToEthermine()");
            IEthermineAPI ethermineAPI = new ApiEthermine();
            Call<ResponseEthermine> callEthermine = ethermineAPI.getResponseFromEthermine();

            callEthermine.enqueue(new Callback<ResponseEthermine>() {
                @Override
                public void onResponse(Call<ResponseEthermine> call, Response<ResponseEthermine> response) {
                    if(response.isSuccessful()) {
                        Log.d(LOG_TAG, "I put response into repository. address in response: " + response.body().getAddress());
                        Repository.getInstance().setLastResponse(response.body());
                        Log.d(LOG_TAG, "I get response from repository. address in response: " + Repository.getInstance().getLastResponseEthermine().getAddress());

                        sendMessageToUI();

                        Intent resultIntent = new Intent(MyService.this, ActivityMain.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                        stackBuilder.addParentStack(ActivityMain.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .setContentTitle("Test notification")
                                .setContentText("Number of notification = " + numberOfNotifications);

                        mBuilder.setContentIntent(pendingIntent);

                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

                        numberOfNotifications++;
                    }
                }

                @Override
                public void onFailure(Call<ResponseEthermine> call, Throwable t) {
                    Log.d(LOG_TAG, "HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEELP");
                }
            });
        }
    }

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COMMAND_REGISTER_CLIENT:
                    Log.d(LOG_TAG, "Is the client registered?" + mClients.add(msg.replyTo));
                    Bundle bundle = msg.getData();
                    ResponseEthermine response = (ResponseEthermine) bundle.getSerializable("test");

                    Log.d(LOG_TAG, response.getAddress() + " + " + response.getAvgHashrate());
                    break;

                case COMMAND_UNREGISTER_CLIENT:
                    Log.d(LOG_TAG, "Is the client unregistered? - " + mClients.remove(msg.replyTo));
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void sendMessageToUI(){
        Log.d(LOG_TAG, "sendMessageToUI: mClients.size = " + mClients.size());
        for(int i = mClients.size()-1; i>=0; i--){

            try {
                Bundle bundle = new Bundle();
                Message msg = Message.obtain(null, COMMAND_MESSAGE_TO_UI);
                ResponseEthermine lastResponseEthermine = Repository.getInstance().getLastResponseEthermine();
                Log.d(LOG_TAG, "Before sending message to UI, lastResponseEthermine = " + lastResponseEthermine);
                if(lastResponseEthermine != null)
//                    bundle.putString(KEY_LAST_RESPONSE_ETHERMINE, "you can get the last response from ethermine");
                    bundle.putSerializable(KEY_LAST_RESPONSE_ETHERMINE, lastResponseEthermine);
                else
                    bundle.putString(KEY_ERROR, "Sorry, but we haven't response from Ethermine.");

                msg.setData(bundle);

                if(mClients.get(i) != null)
                    mClients.get(i).send(msg);
            } catch (RemoteException e) {
                mClients.remove(i); // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
                e.printStackTrace();
            }
        }
    }


    public static class Repository {
        private static ResponseEthermine lastResponseEthermine = null;
        private static ResponseDwarfpool lastResponseDwarfpool = null;
        private static final String LOG_TAG = "MY_LOG: " + Repository.class.getSimpleName();
        private static Repository instance = new Repository();

        public static Repository getInstance(){
            return instance;
        }

        public ResponseEthermine getLastResponseEthermine() {
            return lastResponseEthermine;
        }

        public ResponseDwarfpool getLastResponseDwarfpool() {
            Log.d("Repository", "lastResponseEthermine = " + lastResponseDwarfpool);
            return lastResponseDwarfpool;
        }

        void setLastResponse(IResponse response) {
            Log.d(LOG_TAG, "I have response: " + response);
            if(response instanceof ResponseEthermine) {
                lastResponseEthermine = (ResponseEthermine) response;
//            lastResponseDwarfpool.setTimeOfTheLastUpdate(new Date());
                Log.d(LOG_TAG, "This response from Ethermine. Address in response" +
                        " " + lastResponseEthermine.getAddress());
                lastResponseEthermine.setTimeOfTheLastUpdate(new Date());
                Log.d(LOG_TAG, "Current Hashrate in response" +
                        " " + lastResponseEthermine.getHashRate());
            }
            if(response instanceof ResponseDwarfpool) {
                lastResponseDwarfpool = (ResponseDwarfpool) response;
                lastResponseDwarfpool.setTimeOfTheLastUpdate(new Date());
            }

            Log.d(LOG_TAG, "I send message to subscribers!");
            EventBus.getDefault().post(new MyMessageEvent("Test from repository"));
        }
    }

}
