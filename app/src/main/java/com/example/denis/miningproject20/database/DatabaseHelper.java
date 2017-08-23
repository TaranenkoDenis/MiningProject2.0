package com.example.denis.miningproject20.database;

import android.util.Log;

import com.example.denis.miningproject20.App;
import com.example.denis.miningproject20.models.ethermine.DaoMaster;
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

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;

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
    private QueryBuilder<WorkersEthermine> workersEthermineQuery;

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

        Log.d(LOG_TAG, "putResponseEthermine Test(Before putting): \n"
                + "Number of elements in table MinerStatsEthermine = " + minerStatsEthermineQuery.list().size() + "\n"
                + "Number of elements in table ResponseEthermine = " + responseEthermineQuery.list().size() + "\n"
                + "Number of elements in table RoundEthermine = " + roundEthermineQuery.list().size() + "\n"
                + "Number of elements in table SettingsEthermine = " + settingsEthermineQuery.list().size() + "\n"
                + "Number of elements in table WorkerEthermine = " + workerEthermineQuery.list().size() + "\n");

        Log.d(LOG_TAG, "The number of the responsesEthermine in database: " + getNumberRsponsesEthermineInDB(response.getAddress()));

        // set to db the MinerStats
        Long maxMinerStatsId = minerStatsEthermineDao.insert(response.getMinerStats());
        response.setMinerStatsId(maxMinerStatsId);
        Log.d(LOG_TAG, "Id in new minerStats object: " + maxMinerStatsId
                + "; number of objects in this table = " + minerStatsEthermineQuery.list().size());

        // set to db the settings
        Long maxSettingsId = settingsEthermineDao.insert(response.getSettings());
        response.setSettingsId(maxSettingsId);
        Log.d(LOG_TAG, "Id in new settings object: " + maxSettingsId
                + "; number of objects in this table = " + settingsEthermineQuery.list().size());

        // Set to db the response
        Long responseId = responseEthermineDao.insert(response);

        // Set to db the rounds
        for(RoundEthermine round : response.getRounds())
            round.setResponseId(responseId);
        roundEthermineDao.insertInTx(response.getRounds());

        // Set to db the workers
        Log.d(LOG_TAG, "Number of workers in response = " + response.getWorkers().getWorkersEthermine().size());
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

    private int getNumberRsponsesEthermineInDB(String wallet) {
        List<ResponseEthermine> list = responseEthermineQuery
                .where(ResponseEthermineDao.Properties.Address.eq(wallet))
                .list();
        return list.size();
    }

    public synchronized List<ResponseEthermine> getLastResponsesEthermine(){

        Log.d(LOG_TAG, "Get last responses from Ethermine.");

        // current number of wallet 2
        List<ResponseEthermine> list = new ArrayList<>();

        Log.d(LOG_TAG, "Test. Number of elements with first wallet: " + getNumberRsponsesEthermineInDB(MyService.FIRST_WALLET_ETHERMINE));

        // TODO Здесь в where берутся последние два ответа. Выбираются два ответа с наибольшим id. Изменить это.
        responseEthermineQuery
                    .or(new WhereCondition.StringCondition(ResponseEthermineDao.TABLENAME + "._id = (SELECT max(_id) FROM " + ResponseEthermineDao.TABLENAME
                            + " WHERE ADDRESS = \"" + MyService.FIRST_WALLET_ETHERMINE + "\")"),
                    new WhereCondition.StringCondition(ResponseEthermineDao.TABLENAME + "._id = (SELECT max(_id) FROM " + ResponseEthermineDao.TABLENAME
                            + " WHERE ADDRESS = \"" + MyService.SECOND_WALLET_ETHERMINE + "\")"));

        list = responseEthermineQuery.list();

        Log.d(LOG_TAG, "Current list of responses. Size = " + list.size());

        if(list.size() > 0)
            buildResponseEthermine(list);
        else Log.d(LOG_TAG, "HEEEEELP, problem with db.");

        return list;
    }

    private void buildResponseEthermine(List<ResponseEthermine> list) {
        for (ResponseEthermine response : list) {

            Log.d(LOG_TAG, "Current element in buildResponseEthermine before join:");
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

            Log.d(LOG_TAG, "Current element in buildResponseEthermine after join:");
            response.checkResponse();
        }
    }

    public String getDatabaseName() {
        return daoSession.getDatabase().toString();
    }

    public List<ResponseEthermine> getResponsesEthermine(String walletEthermine) {
        return responseEthermineQuery
                .where(ResponseEthermineDao.Properties.Address.eq(walletEthermine))
                .list();
    }
}
