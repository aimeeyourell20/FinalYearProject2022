package com.example.finalyearproject;

public class MentorFriendlist {

    public String date, profileimage;

    public MentorFriendlist(){

    }

    public MentorFriendlist(String date, String profileimage) {
        this.date = date;
        this.profileimage = profileimage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}
