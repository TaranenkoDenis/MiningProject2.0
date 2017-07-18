package com.example.denis.miningproject20.models;

import com.example.denis.miningproject20.ItemsRecyclerView;

/**
 * Created by denis on 09.07.17.
 */

public class NameOfPools implements ItemsRecyclerView {
    private String nameOfPool;

    public NameOfPools(String nameOfPool) {
        this.nameOfPool = nameOfPool;
    }

    public String getNameOfPool() {
        return nameOfPool;
    }
}
