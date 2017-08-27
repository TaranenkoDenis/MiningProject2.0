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
        List<ResponseEthermine> list;

        Log.d(LOG_TAG, "Test. Number of elements in table ResponseEthermine" +
                ": " + getNumberRsponsesEthermineInDB());

        // TODO Здесь в where берутся последние два ответа. Выбираются два ответа с наибольшим id. Изменить это.
        QueryBuilder<ResponseEthermine> lastResponsesEthermineFirstWalletQuery = responseEthermineDao.queryBuilder()
                    .where(new WhereCondition.StringCondition(
                            "\"RESPONSE_ETHERMINE._id\" = (SELECT max(\"RESPONSE_ETHERMINE._id\") FROM " + ResponseEthermineDao.TABLENAME
                            + " WHERE \"RESPONSE_ETHERMINE.ADDRESS\" = \"" + MyService.FIRST_WALLET_ETHERMINE + "\")"));

        QueryBuilder<ResponseEthermine> lastResponsesEthermineSecondWalletQuery = responseEthermineDao.queryBuilder()
                .where(new WhereCondition.StringCondition(
                "\"RESPONSE_ETHERMINE._id\" = (SELECT max(\"RESPONSE_ETHERMINE._id\") FROM " + ResponseEthermineDao.TABLENAME
                        + " WHERE RESPONSE_ETHERMINE.\"ADDRESS\" = \"" + MyService.SECOND_WALLET_ETHERMINE + "\")"));

        Log.d(LOG_TAG, "Last response with first wallet(" + MyService.FIRST_WALLET_ETHERMINE+")");
        for(ResponseEthermine response : lastResponsesEthermineFirstWalletQuery.list())
            response.checkResponse();

        Log.d(LOG_TAG, "Last response with second wallet(" + MyService.SECOND_WALLET_ETHERMINE+")");
        for(ResponseEthermine response : lastResponsesEthermineSecondWalletQuery.list())
            response.checkResponse();

//        Log.d(LOG_TAG, "All responses:");
//        for(ResponseEthermine response : getAllResponsesEthermine())
//            response.checkResponse();


        list = lastResponsesEthermineFirstWalletQuery.list();

        Log.d(LOG_TAG, "Current list of responses. Size = " + list.size());

        if(list.size() > 0)
            buildResponsesEthermine(list);
        else Log.d(LOG_TAG, "HEEEEELP, problem with db.");

        return list;
    }

    public List<ResponseEthermine> getAllResponsesEthermine() {
        List<ResponseEthermine> list = responseEthermineQuery.list();
        buildResponsesEthermine(list);
        return list;
    }

    private void buildResponsesEthermine(List<ResponseEthermine> list) {
        for (ResponseEthermine response : list) {

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
    }

    public String getDatabaseName() {
        return daoSession.getDatabase().toString();
    }

    public List<ResponseEthermine> getResponsesEthermine(String walletEthermine) {

        List<ResponseEthermine> list =
                responseEthermineQuery
                .where(ResponseEthermineDao.Properties.Address.eq(walletEthermine))
                .list();
        buildResponsesEthermine(list);
        return list;
    }
}
