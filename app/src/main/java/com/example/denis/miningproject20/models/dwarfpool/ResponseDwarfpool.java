package com.example.denis.miningproject20.models.dwarfpool;

import com.example.denis.miningproject20.models.IResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by denis on 12.07.17.
 */

public class ResponseDwarfpool implements IResponse, Serializable{
    private Date timeOfTheLastUpdate;

    public Date getTimeOfTheLastUpdate() {
        return timeOfTheLastUpdate;
    }

    public void setTimeOfTheLastUpdate(Date timeOfTheLastUpdate) {
        this.timeOfTheLastUpdate = timeOfTheLastUpdate;
    }
}
