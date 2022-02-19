package com.example.finalyearproject;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class FindMentor implements Serializable{

    public String type, name, bio, location, language, skill1, skill2, skill3, key, industry, company, photo;

    public FindMentor()
    {

    }

    public FindMentor(String type, String name, String bio, String location, String language, String skill1, String skill2, String skill3, String key, String industry, String company, String photo)
    {
        this.type = type;
        this.name = name;
        this.bio = bio;
        this.location = location;
        this.language = language;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.key = key;
        this.industry = industry;
        this.company = company;
        this.photo = photo;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}