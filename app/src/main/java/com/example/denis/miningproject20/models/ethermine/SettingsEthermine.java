package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by denis on 29.06.17.
 */

@Entity(active = true)

public class SettingsEthermine implements Serializable{

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("miner")
    @Expose
    private String miner;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("monitor")
    @Expose
    private Long monitor;
    @SerializedName("minPayout")
    @Expose
    private Long minPayout;

    private final static long serialVersionUID = 1164269290012137915L;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 286188344)
    private transient SettingsEthermineDao myDao;

    @Generated(hash = 13922856)
    public SettingsEthermine(Long id, String miner, String email, Long monitor,
            Long minPayout) {
        this.id = id;
        this.miner = miner;
        this.email = email;
        this.monitor = monitor;
        this.minPayout = minPayout;
    }

    @Generated(hash = 2035431937)
    public SettingsEthermine() {
    }

    public String getMiner() {
        return miner;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMonitor() {
        return monitor;
    }

    public void setMonitor(Long monitor) {
        this.monitor = monitor;
    }

    public Long getMinPayout() {
        return minPayout;
    }

    public void setMinPayout(Long minPayout) {
        this.minPayout = minPayout;
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

    public String getContentOfSettings(){
        return "id = " + this.id + ",\n"
                + "miner = " + this.email + ",\n"
                + "email = " + this.email + ",\n"
                + "monitor = " + this.monitor + ",\n"
                + "minPayout = " + this.minPayout + ",\n";
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1219738338)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSettingsEthermineDao() : null;
    }
}
