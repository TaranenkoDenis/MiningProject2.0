package com.example.denis.miningproject20.models.ethermine;

import com.example.denis.miningproject20.ItemsRecyclerView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by denis on 29.06.17.
 */

@Entity(active = true)

public class WorkerEthermine implements ItemsRecyclerView, Serializable {

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("worker")
    @Expose
    private String worker;
    @SerializedName("hashrate")
    @Expose
    private String hashrate;
    @SerializedName("reportedHashRate")
    @Expose
    private String reportedHashRate;
    @SerializedName("validShares")
    @Expose
    private Long validShares;
    @SerializedName("invalidShares")
    @Expose
    private Long invalidShares;
    @SerializedName("staleShares")
    @Expose
    private Long staleShares;
    @SerializedName("workerLastSubmitTime")
    @Expose
    private Long workerLastSubmitTime;
    @SerializedName("invalidShareRatio")
    @Expose
    private Long invalidShareRatio;

    private Long responseId;

    private final static long serialVersionUID = -5303263359818635190L;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 336725501)
    private transient WorkerEthermineDao myDao;



    @Generated(hash = 816844785)
    public WorkerEthermine() {
    }

    @Generated(hash = 774217107)
    public WorkerEthermine(Long id, String worker, String hashrate, String reportedHashRate,
            Long validShares, Long invalidShares, Long staleShares, Long workerLastSubmitTime,
            Long invalidShareRatio, Long responseId) {
        this.id = id;
        this.worker = worker;
        this.hashrate = hashrate;
        this.reportedHashRate = reportedHashRate;
        this.validShares = validShares;
        this.invalidShares = invalidShares;
        this.staleShares = staleShares;
        this.workerLastSubmitTime = workerLastSubmitTime;
        this.invalidShareRatio = invalidShareRatio;
        this.responseId = responseId;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getHashrate() {
        return hashrate;
    }

    public void setHashrate(String hashrate) {
        this.hashrate = hashrate;
    }

    public String getReportedHashRate() {
        return reportedHashRate;
    }

    public void setReportedHashRate(String reportedHashRate) {
        this.reportedHashRate = reportedHashRate;
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

    public Long getWorkerLastSubmitTime() {
        return workerLastSubmitTime;
    }

    public void setWorkerLastSubmitTime(Long workerLastSubmitTime) {
        this.workerLastSubmitTime = workerLastSubmitTime;
    }

    public Long getInvalidShareRatio() {
        return invalidShareRatio;
    }

    public void setInvalidShareRatio(Long invalidShareRatio) {
        this.invalidShareRatio = invalidShareRatio;
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

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1925959301)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWorkerEthermineDao() : null;
    }
}
