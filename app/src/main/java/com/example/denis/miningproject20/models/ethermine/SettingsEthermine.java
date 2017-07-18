package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by denis on 29.06.17.
 */

class SettingsEthermine implements Serializable{
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
}
