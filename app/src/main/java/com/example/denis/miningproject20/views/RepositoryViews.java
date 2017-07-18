package com.example.denis.miningproject20.views;

import com.example.denis.miningproject20.models.dwarfpool.ResponseDwarfpool;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.service.MyService;

/**
 * Created by denis on 18.07.17.
 */

class RepositoryViews {
    private static final RepositoryViews ourInstance = new RepositoryViews();
    private static ResponseDwarfpool lastResponseDwarfpool;
    private static ResponseEthermine lastResponseEthermine;
    private static final String LOG_TAG = "MY_LOG: " + RepositoryViews.class.getSimpleName();

    static RepositoryViews getInstance() {
        return ourInstance;
    }

    private RepositoryViews() {
    }

    ResponseDwarfpool getLastResponseDwarfpool(){
        return lastResponseDwarfpool;
    }

    ResponseEthermine getLastResponseEthermine() {
        return lastResponseEthermine;
    }

    void setLastResponseDwarfpool(ResponseDwarfpool lastResponseDwarfpool) {
        RepositoryViews.lastResponseDwarfpool = lastResponseDwarfpool;
    }

    void setLastResponseEthermine(ResponseEthermine lastResponseEthermine) {
        RepositoryViews.lastResponseEthermine = lastResponseEthermine;
    }
}
