package com.example.denis.miningproject20.network;

import android.util.Log;

import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkersEthermine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denis on 09.07.17.
 */

public class ApiEthermine implements IEthermineAPI {


    private static final String LOG_TAG = "MY_LOG: " + ApiEthermine.class.getSimpleName();
    private static IEthermineService ethermineService = null;

    @Override
    public Call<ResponseEthermine> getResponseFromEthermine(String wallet) {

        if (ethermineService == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Gson g = new GsonBuilder().registerTypeAdapter(WorkersEthermine.class, new WorkersEthermineDeserializer()).create();

            Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(g))
                .client(client)
                .baseUrl("https://ethermine.org/")
                .build();

            ethermineService = retrofit.create(IEthermineService.class);
        }

        Log.d(LOG_TAG, "ethermineService = " +ethermineService);
        // TODO Think about how I can to do this better
        return ethermineService.getDataFromEthermine(wallet);
    }
}
