package com.example.denis.miningproject20.database;

import android.util.Log;

import com.example.denis.miningproject20.App;
import com.example.denis.miningproject20.models.ethermine.DaoSession;
import com.example.denis.miningproject20.models.ethermine.MinerStatsEthermine;
import com.example.denis.miningproject20.models.ethermine.MinerStatsEthermineDao;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermineDao;
import com.example.denis.miningproject20.models.ethermine.RoundEthermine;
import com.example.denis.miningproject20.models.ethermine.RoundEthermineDao;
import com.example.denis.miningproject20.models.ethermine.SettingsEthermine;
import com.example.denis.miningproject20.models.ethermine.SettingsEthermineDao;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkerEthermineDao;
import com.example.denis.miningproject20.models.ethermine.WorkersEthermine;
import com.example.denis.miningproject20.service.MyService;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 22.07.17.
 */

public class DatabaseHelper {
    private static String LOG_TAG = "MY_LOG: " + DatabaseHelper.class.getSimpleName();

    private static DaoSession daoSession;
    private static final DatabaseHelper instance = new DatabaseHelper();

    private ResponseEthermineDao responseEthermineDao;
    private SettingsEthermineDao settingsEthermineDao;
    private MinerStatsEthermineDao minerStatsEthermineDao;
    private WorkerEthermineDao workerEthermineDao;
    private RoundEthermineDao roundEthermineDao;

    private QueryBuilder<SettingsEthermine> settingsEthermineQuery;
    private QueryBuilder<ResponseEthermine> responseEthermineQuery;
    private QueryBuilder<MinerStatsEthermine> minerStatsEthermineQuery;
    private QueryBuilder<WorkerEthermine> workerEthermineQuery;
    private QueryBuilder<RoundEthermine> roundEthermineQuery;

    private DatabaseHelper(){;
        daoSession = App.getDaoSession();
        QueryBuilder.LOG_SQL = true;

        responseEthermineDao = daoSession.getResponseEthermineDao();
        responseEthermineQuery = responseEthermineDao
                .queryBuilder();

        settingsEthermineDao = daoSession.getSettingsEthermineDao();
        settingsEthermineQuery = settingsEthermineDao
                .queryBuilder();

        minerStatsEthermineDao = daoSession.getMinerStatsEthermineDao();
        minerStatsEthermineQuery = minerStatsEthermineDao
                .queryBuilder();

        workerEthermineDao = daoSession.getWorkerEthermineDao();
        workerEthermineQuery = workerEthermineDao
                .queryBuilder();

        roundEthermineDao = daoSession.getRoundEthermineDao();
        roundEthermineQuery = roundEthermineDao
                .queryBuilder();
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    public synchronized void clearEthermineTables(){
        Log.d(LOG_TAG, "Clear ethermine db");
        responseEthermineDao.deleteAll();
        settingsEthermineDao.deleteAll();
        minerStatsEthermineDao.deleteAll();
        workerEthermineDao.deleteAll();
        roundEthermineDao.deleteAll();
    }


    /**
     * Response хранит id MinerStats и Settings. id Response, в свою очередь, хранятся в каждом Round и Worker
     * @param response
     */
    public synchronized void putResponseEthermineToDatabase(ResponseEthermine response){

        Log.d(LOG_TAG, "putResponseEthermine Test(Before putting)");
        Log.d(LOG_TAG, "The number of the responsesEthermine in database: " + getNumberRsponsesEthermineInDB());

        // set to db the MinerStats
        Long maxMinerStatsId = minerStatsEthermineDao.insert(response.getMinerStats());
        response.setMinerStatsId(maxMinerStatsId);

        // set to db the settings
        Long maxSettingsId = settingsEthermineDao.insert(response.getSettings());
        response.setSettingsId(maxSettingsId);

        // Set to db the response
        Long responseId = responseEthermineDao.insert(response);
        Log.d(LOG_TAG, "responseId after inserting the response to db = " + responseId);
        Log.d(LOG_TAG, "The number of the responsesEthermine in database: " + getNumberRsponsesEthermineInDB());
        Log.d(LOG_TAG, "The responseEthermine from db with id = " + responseId + ": " + responseEthermineDao.load(responseId));

        // Set to db the rounds
        for(RoundEthermine round : response.getRounds())
            round.setResponseId(responseId);
        roundEthermineDao.insertInTx(response.getRounds());

        // Set to db the workers
        for(WorkerEthermine worker: response.getWorkers().getWorkersEthermine())
            worker.setResponseId(responseId);
        workerEthermineDao.insertInTx(response.getWorkers().getWorkersEthermine());

        Log.d(LOG_TAG, "putResponseEthermine Test(after putting): \n"
                + "Number of elements in table MinerStatsEthermine = " + minerStatsEthermineQuery.list().size() + "\n"
                + "Number of elements in table ResponseEthermine = " + responseEthermineQuery.list().size() + "\n"
                + "Number of elements in table RoundEthermine = " + roundEthermineQuery.list().size() + "\n"
                + "Number of elements in table SettingsEthermine = " + settingsEthermineQuery.list().size() + "\n"
                + "Number of elements in table WorkerEthermine = " + workerEthermineQuery.list().size() + "\n");
    }

    private int getNumberRsponsesEthermineInDB() {
        List<ResponseEthermine> list = responseEthermineQuery
                .list();
        return list.size();
    }

    public synchronized List<ResponseEthermine> getLastResponsesEthermine(){

        Log.d(LOG_TAG, "Get last responses from Ethermine.");

        // current number of wallet 2
        // TODO Change this !
        List<ResponseEthermine> list = new ArrayList<>();
        ResponseEthermine tmp = getLastResponseEthermine(MyService.FIRST_WALLET_ETHERMINE);
        if(tmp != null) {
            Log.d(LOG_TAG, "First if.");
            list.add(tmp);
        }
        tmp = getLastResponseEthermine(MyService.SECOND_WALLET_ETHERMINE);
        if(tmp != null) {
            Log.d(LOG_TAG, "Second if.");
            list.add(tmp);
        }

        Log.d(LOG_TAG, "Test. Number of elements in table ResponseEthermine" +
                ": " + getNumberRsponsesEthermineInDB());

        // TODO Здесь в where берутся последние два ответа. Выбираются два ответа с наибольшим id. Изменить это.

//        Log.d(LOG_TAG, "All responses:");
//        for(ResponseEthermine response : getAllResponsesEthermine())
//            response.checkResponse();


        Log.d(LOG_TAG, "Current list of responses. Size = " + list.size());

        return list;
    }

    public List<ResponseEthermine> getAllResponsesEthermine() {
        List<ResponseEthermine> list = responseEthermineQuery.list();
        for (ResponseEthermine response : list)
            buildResponseEthermine(response);
        return list;
    }

    private void buildResponseEthermine(ResponseEthermine response){
        Log.d(LOG_TAG, "Current element in buildResponsesEthermine before join:");
        response.checkResponse();

        // Join one-to-one
        QueryBuilder<MinerStatsEthermine> tmpQueryMinerStats = minerStatsEthermineDao
                .queryBuilder()
                .where(MinerStatsEthermineDao.Properties.Id.eq(response.getMinerStatsId()));
        response.setMinerStats(tmpQueryMinerStats.unique());

        QueryBuilder<SettingsEthermine> tmpQuerySettings = settingsEthermineDao
                .queryBuilder()
                .where(SettingsEthermineDao.Properties.Id.eq(response.getSettingsId()));
        response.setSettings(tmpQuerySettings.unique());

        // Join one-to-many
        QueryBuilder<RoundEthermine> tmpQueryRounds = roundEthermineDao
                .queryBuilder()
                .where(RoundEthermineDao.Properties.ResponseId.eq(response.getId()));
        response.setRounds(tmpQueryRounds.list());

        // Join one-to-many
        QueryBuilder<WorkerEthermine> tmpQueryWorkers = workerEthermineDao
                .queryBuilder()
                .where(WorkerEthermineDao.Properties.ResponseId.eq(response.getId()));
        response.setWorkers(new WorkersEthermine(tmpQueryWorkers.list()));

        Log.d(LOG_TAG, "Current element in buildResponsesEthermine after join:");
        response.checkResponse();
    }

    public String getDatabaseName() {
        return daoSession.getDatabase().toString();
    }

    public List<ResponseEthermine> getResponsesEthermine(String walletEthermine) {

        List<ResponseEthermine> list =
                responseEthermineQuery
                .where(ResponseEthermineDao.Properties.Address.eq(walletEthermine))
                .list();

        for (ResponseEthermine response : list)
            buildResponseEthermine(response);

        return list;
    }

    public ResponseEthermine getLastResponseEthermine(String wallet) {

        ResponseEthermine resultResponse;
        List<ResponseEthermine> responses = responseEthermineDao.queryBuilder()
                .where(ResponseEthermineDao.Properties.Address.eq(wallet))
                .orderDesc(ResponseEthermineDao.Properties.Id)
//                .where(new WhereCondition.StringCondition(
//                        "\"RESPONSE_ETHERMINE._id\" = (SELECT max(\"RESPONSE_ETHERMINE._id\") FROM " + ResponseEthermineDao.TABLENAME
//                                + " WHERE \"RESPONSE_ETHERMINE.ADDRESS\" = \"" + wallet + "\")"))
                .list();

        if(responses.size() > 0){
        Log.d(LOG_TAG, "Last response with wallet(" + wallet +") = " + responses.get(0));

            resultResponse = responses.get(0);
            buildResponseEthermine(resultResponse);
            return resultResponse;
        }
        Log.d(LOG_TAG, "Size of the list of the responses with wallet(" + wallet +") = " + responses.size());
        return null;
    }
}
