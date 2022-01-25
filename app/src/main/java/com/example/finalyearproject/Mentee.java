package com.example.finalyearproject;

import android.widget.Spinner;

public class Mentee {

    public String type, name, goals, language, skills, college, course, occupation;
    public Spinner location;

    public Mentee()
    {

    }

    public Mentee(String type, String name, String goals, String language, String skills, String college, String course, String occupation, Spinner location) {
        this.type = type;
        this.name = name;
        this.goals = goals;
        this.language = language;
        this.skills = skills;
        this.college = college;
        this.course = course;
        this.occupation = occupation;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public Spinner getLocation() {
        return location;
    }

    public void setLocation(Spinner location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
