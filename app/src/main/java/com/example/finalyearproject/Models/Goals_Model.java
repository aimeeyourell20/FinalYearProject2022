package com.example.finalyearproject.Models;

public class Goals_Model {

    String goalsTitle, goalsDescription, goalsDate, goalsid, goalsStartDate, goalsMentee, goalsMentor, status, goalsmentorid;


    public Goals_Model() {
    }

    public Goals_Model(String goalsTitle, String goalsDescription, String goalsDate, String goalsid, String goalsStartDate, String goalsMentee, String goalsMentor, String status, String goalsmentorid) {
        this.goalsTitle = goalsTitle;
        this.goalsDescription = goalsDescription;
        this.goalsDate = goalsDate;
        this.goalsid = goalsid;
        this.goalsStartDate = goalsStartDate;
        this.goalsMentee = goalsMentee;
        this.goalsMentor = goalsMentor;
        this.status = status;
        this.goalsmentorid = goalsmentorid;
    }

    public Goals_Model(String mTask, String mDescription, String id, String mdate) {
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

    public String getGoalsid() {
        return goalsid;
    }

    public void setGoalsid(String goalsid) {
        this.goalsid = goalsid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoalsmentorid() {
        return goalsmentorid;
    }

    public void setGoalsmentorid(String goalsmentorid) {
        this.goalsmentorid = goalsmentorid;
    }
}
