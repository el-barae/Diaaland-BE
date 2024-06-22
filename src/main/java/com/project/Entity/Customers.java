package com.project.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private String phoneNumber;
    @Column
    private String url;
    @Column
    private String description;
    @Column
    private String logo;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Customers updateCustomer(Customers newCustomer) {
        // Update only non-null fields
        if (newCustomer.getName() != null) {
            this.setName(newCustomer.getName());
        }
        if (newCustomer.getEmail() != null) {
            this.setEmail(newCustomer.getEmail());
        }
        if (newCustomer.getAddress() != null) {
            this.setAddress(newCustomer.getAddress());
        }
        if (newCustomer.getCity() != null) {
            this.setCity(newCustomer.getCity());
        }
        if (newCustomer.getCountry() != null) {
            this.setCountry(newCustomer.getCountry());
        }
        if (newCustomer.getPhoneNumber() != null) {
            this.setPhoneNumber(newCustomer.getPhoneNumber());
        }
        if (newCustomer.getUrl() != null) {
            this.setUrl(newCustomer.getUrl());
        }
        if (newCustomer.getDescription() != null) {
            this.setDescription(newCustomer.getDescription());
        }
        if (newCustomer.getLogo() != null) {
            this.setLogo(newCustomer.getLogo());
        }
        // Do not update id and user attributes
        return this;
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Customers c = new Customers();

        public Builder phoneNumber(String p) {
            c.phoneNumber = p;
            return this;
        }

        public Builder email(String email) {
            c.email = email;
            return this;
        }
        public Builder name(String n) {
            c.name = n;
            return this;
        }

        public Customers.Builder user(User u) {
            c.user = u;
            return this;
        }

        public Customers build() {
            return c;
        }
    }

    public Customers(Long id, String name, String email, String address, String city, String country, String phoneNumber, String url, String description, String logo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.url = url;
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
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUrl() {
        return url;
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
    
    public void setPhoneNumber(String p) {
        this.phoneNumber = p;
    }

    public void setUrl(String p) {
        this.url = p;
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
                phoneNumber + "|~" +
                url + "|~" +
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

