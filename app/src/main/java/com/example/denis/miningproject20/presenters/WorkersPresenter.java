package com.example.denis.miningproject20.presenters;

import android.util.Log;

import com.example.denis.miningproject20.network.ApiEthermine;
import com.example.denis.miningproject20.network.IEthermineAPI;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.views.IWorkersView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by denis on 10.07.17.
 */

public class WorkersPresenter implements IWorkersPresenter {

    IWorkersView view;
    ResponseEthermine responseEthermine;
    IEthermineAPI ethermineApi = new ApiEthermine();
    private final String LOG = WorkersPresenter.class.getSimpleName();

    public WorkersPresenter(IWorkersView view) {
        this.view = view;
    }

    public void getResponseFromEthermine() {
        Call<ResponseEthermine> dataObservable = ethermineApi.getResponseFromEthermine();

        dataObservable.enqueue(new Callback<ResponseEthermine>() {
            @Override
            public void onResponse(Call<ResponseEthermine> call, Response<ResponseEthermine> response) {
                Log.d(LOG, "I HAVE RESPONSE. We Have workers: " + response.body().getWorkers().getWorkersEthermine());
                view.setWorkers(response.body().getWorkers().getWorkersEthermine());
            }

            @Override
            public void onFailure(Call<ResponseEthermine> call, Throwable t) {
                Log.d(LOG, "HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEELP");
            }
        });

    }
}
