package com.example.finalyearproject.Models;

public class Jobs {

    private String job_title;
    private String job_location;
    private String job_description;
    private String company_name;
    private String link;
    private String id;
    private String description;



    public Jobs( String job_title, String job_location, String company_name, String description, String id) {
        this.job_title = job_title;
        this.job_location = job_location;
        this.company_name = company_name;
        this.description = description;
        this.id = id;

    }

    public Jobs(String description) {

        this.description = description;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
    }
}
