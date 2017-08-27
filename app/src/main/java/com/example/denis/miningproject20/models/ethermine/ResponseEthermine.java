package com.example.denis.miningproject20.models.ethermine;

import android.util.Log;

import com.example.denis.miningproject20.models.IResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by denis on 29.06.17.
 */

@Entity(active = true)

public class ResponseEthermine implements IResponse, Serializable {

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("address")
    @Expose
    private String address;

    private Long workersId;
    @SerializedName("workers")
    @Expose
//    @ToOne(joinProperty = "workersId")
    @Transient
    private WorkersEthermine workers;
    @SerializedName("unpaid")
    @Expose
    private Long unpaid;

    @SerializedName("rounds")
    @Expose
//    @ToMany(referencedJoinProperty = "responseId")
    @Transient
    private List<RoundEthermine> rounds;

    private Long settingsId;
    @SerializedName("settings")
    @Expose
//    @ToOne(joinProperty = "settingsId")
    @Transient
    private SettingsEthermine settings;
    @SerializedName("hashRate")
    @Expose
    private String hashRate;
    @SerializedName("avgHashrate")
    @Expose
    private Double avgHashrate;
    @SerializedName("reportedHashRate")
    @Expose
    private String reportedHashRate;

    private Long minerStatsId;
    @SerializedName("minerStats")
    @Expose
//    @ToOne(joinProperty = "minerStatsId")
    @Transient
    private MinerStatsEthermine minerStats;
    @SerializedName("ethPerMin")
    @Expose
    private Double ethPerMin;
    @SerializedName("usdPerMin")
    @Expose
    private Double usdPerMin;
    @SerializedName("btcPerMin")
    @Expose
    private Double btcPerMin;

    private Long dateUpdate;

    private final static long serialVersionUID = 4364269290012137915L;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1920834251)
    private transient ResponseEthermineDao myDao;


    @Generated(hash = 2114591239)
    public ResponseEthermine() {
    }

    @Generated(hash = 1721832168)
    public ResponseEthermine(Long id, String address, Long workersId, Long unpaid,
            Long settingsId, String hashRate, Double avgHashrate, String reportedHashRate,
            Long minerStatsId, Double ethPerMin, Double usdPerMin, Double btcPerMin,
            Long dateUpdate) {
        this.id = id;
        this.address = address;
        this.workersId = workersId;
        this.unpaid = unpaid;
        this.settingsId = settingsId;
        this.hashRate = hashRate;
        this.avgHashrate = avgHashrate;
        this.reportedHashRate = reportedHashRate;
        this.minerStatsId = minerStatsId;
        this.ethPerMin = ethPerMin;
        this.usdPerMin = usdPerMin;
        this.btcPerMin = btcPerMin;
        this.dateUpdate = dateUpdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Long unpaid) {
        this.unpaid = unpaid;
    }

    public String getHashRate() {
        return hashRate;
    }

    public void setHashRate(String hashRate) {
        this.hashRate = hashRate;
    }

    public Double getAvgHashrate() {
        return avgHashrate;
    }

    public void setAvgHashrate(Double avgHashrate) {
        this.avgHashrate = avgHashrate;
    }

    public String getReportedHashRate() {
        return reportedHashRate;
    }

    public void setReportedHashRate(String reportedHashRate) {
        this.reportedHashRate = reportedHashRate;
    }

    public Double getEthPerMin() {
        return ethPerMin;
    }

    public void setEthPerMin(Double ethPerMin) {
        this.ethPerMin = ethPerMin;
    }

    public Double getUsdPerMin() {
        return usdPerMin;
    }

    public void setUsdPerMin(Double usdPerMin) {
        this.usdPerMin = usdPerMin;
    }

    public Double getBtcPerMin() {
        return btcPerMin;
    }

    public void setBtcPerMin(Double btcPerMin) {
        this.btcPerMin = btcPerMin;
    }

    public Long getDate() {
        return dateUpdate;
    }

    public void setDate(Long timeOfTheLastUpdate) {
        this.dateUpdate = timeOfTheLastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public void checkResponse(){
        String LOG_TAG = "MY_LOG " + ResponseEthermine.class.getSimpleName();
        Log.d(LOG_TAG, "id = " + id + ",\n"
                + "address = " + address + ",\n"
                + "unpaid = " + unpaid + ",\n"
                + "hashRate = " + hashRate + ",\n"
                + "avgHashrate = " + avgHashrate + ",\n"
                + "settingsId = " + settingsId + ",\n"
                + "minerStatsId = " + minerStatsId + ",\n"
                + "size of workers = " + workers + ",\n"
                + "settings = " + settings + ",\n"
                + "minerStats = " + minerStats + ",\n");
    }

    public Long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public Long getMinerStatsId() {
        return minerStatsId;
    }

    public void setMinerStatsId(Long minerStatsId) {
        this.minerStatsId = minerStatsId;
    }

    public Long getWorkersId() {
        return workersId;
    }

    public void setWorkersId(Long workersId) {
        this.workersId = workersId;
    }


    public MinerStatsEthermine getMinerStats() {
        return minerStats;
    }

    public void setMinerStats(MinerStatsEthermine minerStats) {
        this.minerStats = minerStats;
    }

    public SettingsEthermine getSettings() {
        return settings;
    }

    public void setSettings(SettingsEthermine settings) {
        this.settings = settings;
    }

    public List<RoundEthermine> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundEthermine> rounds) {
        this.rounds = rounds;
    }

    public WorkersEthermine getWorkers() {
        return workers;
    }

    public void setWorkers(WorkersEthermine workers) {
        this.workers = workers;
    }

    public Long getDateUpdate() {
        return this.dateUpdate;
    }

    public void setDateUpdate(Long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2032763334)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getResponseEthermineDao() : null;
    }
}
