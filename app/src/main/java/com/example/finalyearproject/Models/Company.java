package com.example.finalyearproject.Models;

public class Company {

    private String description;
    private String employees;
    private String hq_location;
    private String name;
    private String rating;

    public Company(String description, String employees, String hq_location, String name, String rating) {
        this.description = description;
        this.employees = employees;
        this.hq_location = hq_location;
        this.name = name;
        this.rating = rating;
    }

    public Company() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployees() {
        return employees;
    }

    public void setEmployees(String employees) {
        this.employees = employees;
    }

    public String getHq_location() {
        return hq_location;
    }

    public void setHq_location(String hq_location) {
        this.hq_location = hq_location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

