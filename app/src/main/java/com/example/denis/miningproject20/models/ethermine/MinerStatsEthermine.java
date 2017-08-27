package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by denis on 29.06.17.
 */

@Entity(active = true)

public class MinerStatsEthermine implements Serializable{

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("lastSeen")
    @Expose
    private Long lastSeen;
    @SerializedName("reportedHashrate")
    @Expose
    private Long reportedHashrate;
    @SerializedName("currentHashrate")
    @Expose
    private Double currentHashrate;
    @SerializedName("validShares")
    @Expose
    private Long validShares;
    @SerializedName("invalidShares")
    @Expose
    private Long invalidShares;
    @SerializedName("staleShares")
    @Expose
    private Long staleShares;
    @SerializedName("averageHashrate")
    @Expose
    private Double averageHashrate;
    @SerializedName("activeWorkers")
    @Expose
    private Long activeWorkers;

    private final static long serialVersionUID = -2925860672903152563L;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1092498312)
    private transient MinerStatsEthermineDao myDao;

    @Generated(hash = 881617512)
    public MinerStatsEthermine(Long id, Long time, Long lastSeen,
            Long reportedHashrate, Double currentHashrate, Long validShares,
            Long invalidShares, Long staleShares, Double averageHashrate,
            Long activeWorkers) {
        this.id = id;
        this.time = time;
        this.lastSeen = lastSeen;
        this.reportedHashrate = reportedHashrate;
        this.currentHashrate = currentHashrate;
        this.validShares = validShares;
        this.invalidShares = invalidShares;
        this.staleShares = staleShares;
        this.averageHashrate = averageHashrate;
        this.activeWorkers = activeWorkers;
    }

    @Generated(hash = 1479631126)
    public MinerStatsEthermine() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Long getReportedHashrate() {
        return reportedHashrate;
    }

    public void setReportedHashrate(Long reportedHashrate) {
        this.reportedHashrate = reportedHashrate;
    }

    public Double getCurrentHashrate() {
        return currentHashrate;
    }

    public void setCurrentHashrate(Double currentHashrate) {
        this.currentHashrate = currentHashrate;
    }

    public Long getValidShares() {
        return validShares;
    }

    public void setValidShares(Long validShares) {
        this.validShares = validShares;
    }

    public Long getInvalidShares() {
        return invalidShares;
    }

    public void setInvalidShares(Long invalidShares) {
        this.invalidShares = invalidShares;
    }

    public Long getStaleShares() {
        return staleShares;
    }

    public void setStaleShares(Long staleShares) {
        this.staleShares = staleShares;
    }

    public Double getAverageHashrate() {
        return averageHashrate;
    }

    public void setAverageHashrate(Double averageHashrate) {
        this.averageHashrate = averageHashrate;
    }

    public Long getActiveWorkers() {
        return activeWorkers;
    }

    public void setActiveWorkers(Long activeWorkers) {
        this.activeWorkers = activeWorkers;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2114266671)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMinerStatsEthermineDao() : null;
    }
}
