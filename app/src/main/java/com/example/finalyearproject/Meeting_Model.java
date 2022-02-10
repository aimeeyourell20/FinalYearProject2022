package com.example.finalyearproject;

import java.util.Map;

public class Meeting_Model {

    private String meetingDescription, meetingLocation, meetingMentor, meetingTitle, meetingMentee, Date;



    public Meeting_Model() {
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingMentor() {
        return meetingMentor;
    }

    public void setMeetingMentor(String meetingMentor) {
        this.meetingMentor = meetingMentor;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingMentee() {
        return meetingMentee;
    }

    public void setMeetingMentee(String meetingMentee) {
        this.meetingMentee = meetingMentee;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
