package com.example.finalyearproject;

import android.widget.Spinner;

public class Goals_Model {

    String goalsTitle, goalsDescription, goalsDate, key, goalsStartDate, goalsMentee, goalsMentor;


    public Goals_Model() {
    }

    public Goals_Model(String goalsTitle, String goalsDescription, String goalsDate, String key, String goalsStartDate, String goalsMentee, String goalsMentor) {
        this.goalsTitle = goalsTitle;
        this.goalsDescription = goalsDescription;
        this.goalsDate = goalsDate;
        this.key = key;
        this.goalsStartDate = goalsStartDate;
        this.goalsMentee = goalsMentee;
        this.goalsMentor = goalsMentor;
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

    public String getGoalsStartDate() {
        return goalsStartDate;
    }

    public void setGoalsStartDate(String goalsStartDate) {
        this.goalsStartDate = goalsStartDate;
    }

    public String getGoalsMentee() {
        return goalsMentee;
    }

    public void setGoalsMentee(String goalsMentee) {
        this.goalsMentee = goalsMentee;
    }

    public String getGoalsMentor() {
        return goalsMentor;
    }

    public void setGoalsMentor(String goalsMentor) {
        this.goalsMentor = goalsMentor;
    }
}
