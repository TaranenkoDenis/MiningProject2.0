package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by denis on 29.06.17.
 */

public class MinerStatsEthermine implements Serializable{
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
}
