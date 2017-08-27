package com.example.denis.miningproject20;

import android.util.Log;

import com.example.denis.miningproject20.database.DatabaseHelper;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.service.MyService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by denis on 23.08.17.
 */

public class Repository {

    private static final String LOG_TAG = "MY_LOG: " + Repository.class.getSimpleName();
    private static final DatabaseHelper dbHelper = DatabaseHelper.getInstance();

    public static Map<Long, Integer> getNumberAliveWorkersEthermine(String wallet) {

        Map<Long, Integer> result = new TreeMap<>();

        List<ResponseEthermine> responsesEthermineFirstWaller =
                dbHelper.getResponsesEthermine(wallet);
        // CHECK
        Log.d(LOG_TAG, "getNumberAliveWorkersEthermine => responses from db, first wallet: ");
        for(ResponseEthermine response : responsesEthermineFirstWaller) {
            response.checkResponse();
            result.put(response.getDate(), response.getWorkers().getWorkersEthermine().size());
        }

        return result;
    }
}
