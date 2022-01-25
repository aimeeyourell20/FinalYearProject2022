package com.example.finalyearproject;

import android.widget.Spinner;

public class Goals_Model {

    String goalsTitle, goalsDescription, goalsDate, key;


    public Goals_Model() {
    }

    public Goals_Model(String goalsTitle, String goalsDescription, String goalsDate, String key) {
        this.goalsTitle = goalsTitle;
        this.goalsDescription = goalsDescription;
        this.goalsDate = goalsDate;
        this.key = key;
    }

    public String getGoalsTitle() {
        return goalsTitle;
    }

    public void setGoalsTitle(String goalsTitle) {
        this.goalsTitle = goalsTitle;
    }

    public String getGoalsDescription() {
        return goalsDescription;
    }

    public void setGoalsDescription(String goalsDescription) {
        this.goalsDescription = goalsDescription;
    }

    public String getGoalsDate() {
        return goalsDate;
    }

    public void setGoalsDate(String goalsDate) {
        this.goalsDate = goalsDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
