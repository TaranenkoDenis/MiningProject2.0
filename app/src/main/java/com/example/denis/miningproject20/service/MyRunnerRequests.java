package com.example.denis.miningproject20.service;

import android.util.Log;

import com.example.denis.miningproject20.models.dwarfpool.ResponseDwarfpool;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.network.RestClientEthermine;
import com.example.denis.miningproject20.network.IEthermineAPI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by denis on 20.07.17.
 */

public class MyRunnerRequests implements Runnable{

    private static final String LOG_TAG = "MY_LOG: " + MyRunnerRequests.class.getSimpleName();
    private int id_pool;
    private List<String> listOfWallets = new LinkedList<>();
    private List<ResponseEthermine> responsesEthermine = new ArrayList<>();
    private List<ResponseDwarfpool> responsesDwarfpool = new ArrayList<>();
    private static int numberOfSuccessfulRequests = 0;
    private final WeakReference<MyService> mService;

    MyRunnerRequests(int id_pool, MyService myService) {
        this.id_pool = id_pool;

        mService = new WeakReference<MyService>(myService);

        listOfWallets.add(MyService.FIRST_WALLET_ETHERMINE);
        listOfWallets.add(MyService.SECOND_WALLET_ETHERMINE);
//        listOfWallets.add("0x34faaa028162c4d4e92db6abfa236a8e90ff2fc3");
//        listOfWallets.add("c1c427cd8e6b7ee3b5f30c2e1d3f3c5536ec16f5");
//        listOfWallets.add("0xe47288aa880067e78aa0736db98bb02ce2c5d2a8");

        // TODO Максимум в еденицу времени на ethermine можно сделать 5 запросов
    }

    @Override
    public void run() {
        switch (id_pool){
            case MyService.ID_DWARFPOOL:
                Log.d(LOG_TAG, "MyRun -> request to Dwarfpool");
                requestToDwarfpool();
                break;

            case MyService.ID_ETHERMINE:
                Log.d(LOG_TAG, "MyRun -> request to Ethermine");

                mService.get().lastResponsesEthermine.clear();

                for(String wallet : listOfWallets)
                    requestToEthermine(wallet);

                break;
        }
    }

    private void requestToDwarfpool() {
        Log.d(LOG_TAG, "requestToDwarfpool()");
    }

    private void requestToEthermine(String wallet) {
        boolean result;
        Log.d(LOG_TAG, "requestToEthermine(), wallet = " + wallet);
        IEthermineAPI ethermineAPI = RestClientEthermine.getInstance();

        ethermineAPI.getResponseFromEthermine(wallet).enqueue(new Callback<ResponseEthermine>() {
            @Override
            public void onResponse(Call<ResponseEthermine> call, Response<ResponseEthermine> response) {
//                if(response.isSuccessful()) {
                    Log.d(LOG_TAG, "I have response from server. address in response: " + response.body().getAddress());

                    response.body().setDate(System.currentTimeMillis());
                    mService.get().lastResponsesEthermine.add(response.body());

                    ++numberOfSuccessfulRequests;

                    Log.d(LOG_TAG, "Number of successful requests: " + numberOfSuccessfulRequests);
                    if(numberOfSuccessfulRequests == listOfWallets.size()
                            && mService.get().lastResponsesEthermine.size() == numberOfSuccessfulRequests) {

                        for (ResponseEthermine ignored : mService.get().lastResponsesEthermine){
                            Log.d(LOG_TAG, "Test. Size of list of the responses from ethermine = "
                                    + mService.get().lastResponsesEthermine.size()
                                    + "\nResponseEthermine before sending message to service: ");
                            ignored.checkResponse();
                        }
                        mService.get().sendResponseFromPoolToUI(MyService.FLAG_REQUESTS_TO_ETHERMINE_COMPLITED);
                        numberOfSuccessfulRequests = 0;
                    }
//                   TODO This is an example of notification
//
//                    Intent resultIntent = new Intent(MyService.this, MainActivity.class);
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
//                    stackBuilder.addParentStack(MainActivity.class);
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyService.this)
//                            .setSmallIcon(R.mipmap.ic_launcher_round)
//                            .setDefaults(Notification.DEFAULT_VIBRATE)
//                            .setContentTitle("Test notification")
//                            .setContentText("Number of notification = " + numberOfNotifications);
//
//                    mBuilder.setContentIntent(pendingIntent);
//
//                    NotificationManager notificationManager =
//                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//                }


                // TODO check this code
//        ethermineAPI.getResponseFromEthermine(wallet)
//                .subscribeOn(Schedulers.io())
//                .subscribe(responseEthermine -> {
//                    Log.d(LOG_TAG, "I have response from server. address in response: " + responseEthermine.getAddress());
//
//                    mService.get().lastResponsesEthermine.add(responseEthermine);
//                    responseEthermine.setDate(new Date());
//
//                    ++numberOfSuccessfulRequests;
//
//                    Log.d(LOG_TAG, "Number of successful requests: " + numberOfSuccessfulRequests);
//                    if(numberOfSuccessfulRequests == listOfWallets.size()
//                            && mService.get().lastResponsesEthermine.size() == numberOfSuccessfulRequests) {
//
//                        for (ResponseEthermine ignored : mService.get().lastResponsesEthermine){
//                            Log.d(LOG_TAG, "Test. Size of list of the responses from ethermine = "
//                                    + mService.get().lastResponsesEthermine.size()
//                                    + "\nResponseEthermine before sending message to service: ");
//                            ignored.checkResponse();
//                        }
//                        mService.get().sendResponseFromPoolToUI(MyService.FLAG_REQUESTS_TO_ETHERMINE_COMPLITED);
//                        numberOfSuccessfulRequests = 0;
//                    }
//                });
            }

            @Override
            public void onFailure(Call<ResponseEthermine> call, Throwable t) {
                Log.d(LOG_TAG, "callEthermine.enqueue(new Callback<ResponseEthermine>() => onFailure: " + t.getMessage());
            }
        });
    }
}
