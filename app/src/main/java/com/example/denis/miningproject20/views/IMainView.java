package com.example.denis.miningproject20.views;

/**
 * Created by denis on 09.07.17.
 */

public interface IMainView {
    void setHashRates(String hashRate, String avgHashRate, String reportedHashRate);
    // TODO Think about graph
    void setCurrency(); // TODO Валюта
    void setWorkers(long currentNumber, long generalNumber);

    void setResponse();
}
