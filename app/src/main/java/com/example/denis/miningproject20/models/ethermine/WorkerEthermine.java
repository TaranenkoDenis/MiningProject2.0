package com.example.denis.miningproject20.models.ethermine;

import com.example.denis.miningproject20.ItemsRecyclerView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by denis on 29.06.17.
 */

public class WorkerEthermine implements ItemsRecyclerView, Serializable {

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
}
