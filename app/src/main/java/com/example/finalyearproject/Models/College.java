package com.example.finalyearproject.Models;

public class College {

    private String title;
    private String country;
    private String city;
    private String score;
    private String rank_display;
    private String region;

    public College(String title, String country, String city, String score, String rank_display, String region) {
        this.title = title;
        this.country = country;
        this.city = city;
        this.score = score;
        this.rank_display = rank_display;
        this.region = region;
    }

    public College() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank_display() {
        return rank_display;
    }

    public void setRank_display(String rank_display) {
        this.rank_display = rank_display;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
