package com.example.finalyearproject;

import android.widget.Spinner;

public class Mentor {


    public String name, email, skills1, skills2, langauge, bio, type;
    public Spinner location;

    public Mentor(){

    }

    public Mentor(String name, String email, String skills1, String skills2, String langauge, String bio, String type, Spinner location) {
        this.name = name;
        this.email = email;
        this.skills1 = skills1;
        this.skills2 = skills2;
        this.langauge = langauge;
        this.bio = bio;
        this.type = type;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills1() {
        return skills1;
    }

    public void setSkills1(String skills1) {
        this.skills1 = skills1;
    }

    public String getSkills2() {
        return skills2;
    }

    public void setSkills2(String skills2) {
        this.skills2 = skills2;
    }

    public Spinner getLocation() {
        return location;
    }

    public void setLocation(Spinner location) {
        this.location = location;
    }

    public String getLangauge() {
        return langauge;
    }

    public void setLangauge(String langauge) {
        this.langauge = langauge;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
