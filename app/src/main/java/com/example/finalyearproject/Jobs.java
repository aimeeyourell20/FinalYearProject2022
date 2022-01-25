package com.example.finalyearproject;

public class Jobs {

    private String job_title;
    private String job_location;
    private String job_description;
    private String company_name;
    private String url;



    public Jobs( String job_title, String job_location, String url, String job_description, String company_name) {
        this.job_title = job_title;
        this.job_location = job_location;
        this.url = url;
        this.job_description = job_description;
        this.company_name = company_name;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
