package com.example.denis.miningproject20.views;

import com.example.denis.miningproject20.presenters.IMainPresenter;

/**
 * Created by denis on 09.07.17.
 */

public class MainPresenter implements IMainPresenter {

    private final String LOG = MainPresenter.class.getSimpleName();
    private final IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void getEthermineData(boolean isUpdate) {


//        dataObservable.enqueue(new Callback<ResponseEthermine>() {
//            @Override
//            public void onResponse(Call<ResponseEthermine> call, Response<ResponseEthermine> response) {
//                mainView.setWorkers(4, response.body().getWorkers().getWorkersEthermine().size());
//
//                String strAvgHashRate = convertAvgHashRate(response.body().getAvgHashrate());
//                responseEthermine = response.body();
//                // TODO CHANGE THIS PLEASE !!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                mainView.setHashRates(response.body().getHashRate(),
//                        String.format(convertAvgHashRate(response.body().getAvgHashrate()) + " GH/s"),
//                           response.body().getReportedHashRate());
//                mainView.setResponse();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseEthermine> call, Throwable t) {
//                Log.d(LOG, "HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEELP");
//            }
//        });
    }

}
