package com.example.denis.miningproject20.network;

import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkersEthermine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denis on 09.07.17.
 */

public class RestClientEthermine implements IEthermineAPI {


    private static final String LOG_TAG = "MY_LOG: " + RestClientEthermine.class.getSimpleName();
    private static final String BASE_URL_Ethermine = "https://ethermine.org";
    private static IEthermineService ethermineService = null;
    private static RestClientEthermine instance = null;

    private RestClientEthermine(){
        createEthermineService();
    }

    public static RestClientEthermine getInstance() {
        if(instance == null)
            instance = new RestClientEthermine();
        return instance;
    }


    @Override
    public Call<ResponseEthermine> getResponseFromEthermine(String wallet) {

        if (ethermineService == null)
            createEthermineService();

        // TODO Think about how I can to do this better
        return ethermineService.getDataFromEthermine(wallet);
    }

    private void createEthermineService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson g = new GsonBuilder().registerTypeAdapter(WorkersEthermine.class, new WorkersEthermineDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(g))
                .client(client)
                .baseUrl(BASE_URL_Ethermine)
                .build();

        ethermineService = retrofit.create(IEthermineService.class);
    }
}
