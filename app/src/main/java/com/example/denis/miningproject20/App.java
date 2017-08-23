package com.example.denis.miningproject20;

import android.app.Application;
import android.util.Log;

import com.example.denis.miningproject20.models.dwarfpool.ResponseDwarfpool;
import com.example.denis.miningproject20.models.ethermine.DaoMaster;
import com.example.denis.miningproject20.models.ethermine.DaoSession;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermine;
import com.example.denis.miningproject20.models.ethermine.ResponseEthermineDao;
import com.example.denis.miningproject20.service.MyService;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * Created by denis on 22.07.17.
 */

public class App extends Application{
    private static final String LOG_TAG = "MY_LOG: " + App.class.getSimpleName();

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "responses-db"){
            @Override
            public void onCreate(Database db) {
                super.onCreate(db);

                db.execSQL("CREATE TRIGGER delete_till_5_first AFTER INSERT"
                        + " ON " + ResponseEthermineDao.TABLENAME + " WHEN (select count(*) from " + ResponseEthermineDao.TABLENAME
                        + " WHERE " + ResponseEthermineDao.TABLENAME + ".ADDRESS = \"" + MyService.FIRST_WALLET_ETHERMINE + "\") > 5"
                        + " BEGIN"
                        + " DELETE FROM " + ResponseEthermineDao.TABLENAME
                        + " WHERE _id = (SELECT min(_id) FROM " + ResponseEthermineDao.TABLENAME
                        + " WHERE " + ResponseEthermineDao.TABLENAME + ".ADDRESS = \"" + MyService.FIRST_WALLET_ETHERMINE + "\");"
                        + " END;" );

                db.execSQL("CREATE TRIGGER delete_till_5_second AFTER INSERT"
                        + " ON " + ResponseEthermineDao.TABLENAME + " WHEN (select count(*) from " + ResponseEthermineDao.TABLENAME
                        + " WHERE " + ResponseEthermineDao.TABLENAME + ".ADDRESS = \"" + MyService.SECOND_WALLET_ETHERMINE+ "\") > 5"
                        + " BEGIN"
                        + " DELETE FROM " + ResponseEthermineDao.TABLENAME
                        + " WHERE _id = (SELECT min(_id) FROM " + ResponseEthermineDao.TABLENAME
                        + " WHERE " + ResponseEthermineDao.TABLENAME + ".ADDRESS = \"" + MyService.SECOND_WALLET_ETHERMINE+ "\");"
                        + " END;" );
            }
        };
        Database db = helper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
