package com.example.denis.miningproject20.models.ethermine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by denis on 29.06.17.
 */

public class RoundEthermine implements Serializable{
    @SerializedName("id")
    @Expose
    private Long id;
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
}
