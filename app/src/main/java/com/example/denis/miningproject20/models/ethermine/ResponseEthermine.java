package com.example.denis.miningproject20.models.ethermine;

import com.example.denis.miningproject20.models.IResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 29.06.17.
 */

public class ResponseEthermine implements IResponse, Serializable {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("workers")
    @Expose
    private WorkersEthermine workers;
    @SerializedName("unpaid")
    @Expose
    private Long unpaid;
    @SerializedName("rounds")
    @Expose
    private List<RoundEthermine> rounds = null;
    @SerializedName("settings")
    @Expose
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
    @SerializedName("minerStats")
    @Expose
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

    private Date timeOfTheLastUpdate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WorkersEthermine getWorkers() {
        return workers;
    }

    public void setWorkers(WorkersEthermine workers) {
        this.workers = workers;
    }

    public Long getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Long unpaid) {
        this.unpaid = unpaid;
    }

    public List<RoundEthermine> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundEthermine> rounds) {
        this.rounds = rounds;
    }

    public SettingsEthermine getSettings() {
        return settings;
    }

    public void setSettings(SettingsEthermine settings) {
        this.settings = settings;
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

    public MinerStatsEthermine getMinerStats() {
        return minerStats;
    }

    public void setMinerStats(MinerStatsEthermine minerStats) {
        this.minerStats = minerStats;
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

    public Date getTimeOfTheLastUpdate() {
        return timeOfTheLastUpdate;
    }

    public void setTimeOfTheLastUpdate(Date timeOfTheLastUpdate) {
        this.timeOfTheLastUpdate = timeOfTheLastUpdate;
    }
}
