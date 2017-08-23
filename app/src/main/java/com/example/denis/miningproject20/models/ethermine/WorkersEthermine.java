package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by denis on 06.07.17.
 */
public class WorkersEthermine implements Serializable{

    @Expose
    @SerializedName("workers")
    private List<WorkerEthermine> workersEthermine;

    private final static long serialVersionUID = 2224269290012137915L;

    public WorkersEthermine() {
    }

    public WorkersEthermine(List<WorkerEthermine> list) {
        workersEthermine = list;
    }

    public List<WorkerEthermine> getWorkersEthermine() {
        return workersEthermine;
    }

    public void setWorkersEthermine(List<WorkerEthermine> workersEthermine) {
        this.workersEthermine = workersEthermine;
    }
}
