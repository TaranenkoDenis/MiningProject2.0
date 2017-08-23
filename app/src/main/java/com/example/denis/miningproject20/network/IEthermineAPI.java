package com.example.denis.miningproject20.network;



import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by denis on 29.06.17.
 */

public interface IEthermineAPI {
    Call<ResponseEthermine> getResponseFromEthermine(String wallet);
}
