package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denis on 06.07.17.
 */

public class WorkersEthermine implements Serializable{
    @Expose
    private List<WorkerEthermine> workersEthermine;

    public List<WorkerEthermine> getWorkersEthermine() {
        return workersEthermine;
    }

    public void setWorkersEthermine(List<WorkerEthermine> workersEthermine) {
        this.workersEthermine = workersEthermine;
    }
}
