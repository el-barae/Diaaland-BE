package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String description;
    @Column
    private String logo;

    public Customers(Long id, String name, String email, String address, String city, String country, String description, String logo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.description = description;
        this.logo = logo;
    }

    public Customers() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    @Override
    public String toString() {
        return name + "|~" +
                email + "|~" +
                address + "|~" +
                city + "|~" +
                country + "|~" +
                description + "|~" +
                logo;
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

