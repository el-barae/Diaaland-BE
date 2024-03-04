package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Customers extends User{
    @Column
    private String name;
    @Column
    private String logo;
    @Column
    private String url;

    public Customers(String email, String password, String phoneNumber, String description, String adress, String city, String country, Role role, String name, String logo, String url) {
    	super(email, password, phoneNumber, description, adress, city, country, role);
    	this.name = name;
        this.logo = logo;
    }

    public Customers() {
    }


    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }
    
    public String geturl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return name + "|~" +
               logo+ "|~" +
               url;
    }
    
    /*public List < Jobs > getJobs() {
        return jobs;
    }
    
    public void addJob(Jobs job) {
        jobs.add(job);
    }

    public void setCourses(List < Jobs > jobs) {
        this.jobs = jobs;
    }
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Jobs> jobs = new ArrayList<>();*/
}

