package com.example.denis.miningproject20.network;

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

    @Override
    public Call<ResponseEthermine> getResponseFromEthermine() {

        
//        if(ethermineService == null) {

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

            IEthermineService ethermineService = retrofit.create(IEthermineService.class);
//        }

        // TODO Think about how I can to do this better
        return ethermineService.getDataFromEthermine("0x6ce0a4ce16ab8916af9ec6f811ad410966f0ee80");
    }
}
