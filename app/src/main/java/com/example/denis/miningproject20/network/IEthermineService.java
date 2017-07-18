package com.example.denis.miningproject20.network;



import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by denis on 09.07.17.
 */

public interface IEthermineService {
    @GET("api/miner_new/{address}")
    Call<ResponseEthermine> getDataFromEthermine(@Path("address") String address);
}
