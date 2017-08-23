package com.example.denis.miningproject20.models.ethermine;

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

public class RoundEthermine implements Serializable{

    @Id(autoincrement = true)
    private Long id;
    @SerializedName("id")
    @Expose
    private Long idInResponse;
    @SerializedName("miner")
    @Expose
    private String miner;
    @SerializedName("block")
    @Expose
    private Long block;
    @SerializedName("work")
    @Expose
    private Long work;
    @SerializedName("amount")
    @Expose
    private Long amount;
    @SerializedName("processed")
    @Expose
    private Long processed;

    private Long responseId;

    private final static long serialVersionUID = 8794269290012137915L;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 79833876)
    private transient RoundEthermineDao myDao;

    @Generated(hash = 1991953085)
    public RoundEthermine(Long id, Long idInResponse, String miner, Long block, Long work,
            Long amount, Long processed, Long responseId) {
        this.id = id;
        this.idInResponse = idInResponse;
        this.miner = miner;
        this.block = block;
        this.work = work;
        this.amount = amount;
        this.processed = processed;
        this.responseId = responseId;
    }

    @Generated(hash = 1655214804)
    public RoundEthermine() {
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlock() {
        return block;
    }

    public void setBlock(Long block) {
        this.block = block;
    }

    public Long getWork() {
        return work;
    }

    public void setWork(Long work) {
        this.work = work;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getProcessed() {
        return processed;
    }

    public void setProcessed(Long processed) {
        this.processed = processed;
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

    public Long getIdInResponse() {
        return this.idInResponse;
    }

    public void setIdInResponse(Long idInResponse) {
        this.idInResponse = idInResponse;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1451941704)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRoundEthermineDao() : null;
    }
}
