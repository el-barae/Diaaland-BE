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
public class Candidates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String description;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String accountStatus;
    @Column
    private String phoneNumber;
    @Column
    private String jobStatus;
    @Column
    private String linkedIn;
    @Column
    private String gitHub;
    @Column
    private String portfolio;
    @Column
    private String blog;
    @Column
    private double expectedSalary;
    @Column
    private String resumeLink;
    @Column
    private String photoLink;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Candidates c = new Candidates();

        public Builder firstName(String fn) {
            c.firstName = fn;
            return this;
        }

        public Builder lastName(String ln) {
            c.lastName = ln;
            return this;
        }
        
        public Builder email(String e) {
            c.email = e;
            return this;
        }
        
        public Builder resumeLink(String r) {
            c.resumeLink = r;
            return this;
        }

        public Builder user(User u) {
            c.user = u;
            return this;
        }

        public Candidates build() {
            return c;
        }
    }
    
    public Candidates() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public String getGitHub() {
        return gitHub;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public String getBlog() {
        return blog;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public User getUser(){
        return user;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String e) {
        this.email = e;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
    
    public String getAddress() {
        return address;
    }
    
    // Setter pour address
    public void setAddress(String address) {
        this.address = address;
    }
    
    // Getter pour city
    public String getCity() {
        return city;
    }
    
    // Setter pour city
    public void setCity(String city) {
        this.city = city;
    }
    
    // Getter pour country
    public String getCountry() {
        return country;
    }
    
    // Setter pour country
    public void setCountry(String country) {
        this.country = country;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String toString() {
        return this.firstName + "|~" +
               this.lastName + "|~" +
               this.email + "|~" +
               this.description + "|~" +
               this.address + "|~" +
               this.city + "|~" +
               this.country + "|~" +
               this.phoneNumber + "|~" +
               this.accountStatus + "|~" +
               this.phoneNumber + "|~" +
               this.jobStatus + "|~" +
               this.linkedIn + "|~" +
               this.gitHub + "|~" +
               this.portfolio + "|~" +
               this.blog + "|~" +
               this.expectedSalary + "|~" +
               this.resumeLink + "|~" +
               this.photoLink
               ;
    }


    public Candidates(Long id, String firstName, String lastName, String email, String description, String address, String city, String country, String accountStatus, String phoneNumber, String jobStatus, String linkedIn, String gitHub, String portfolio, String blog, double expectedSalary, String resumeLink, String photoLink) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.accountStatus = accountStatus;
        this.phoneNumber = phoneNumber;
        this.jobStatus = jobStatus;
        this.linkedIn = linkedIn;
        this.gitHub = gitHub;
        this.portfolio = portfolio;
        this.blog = blog;
        this.expectedSalary = expectedSalary;
        this.resumeLink = resumeLink;
        this.photoLink = photoLink;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    /*
    @OneToMany(mappedBy = "candidate")
    private List<CandidateLinks> candidatesLinks;
    
    @OneToMany(mappedBy = "candidate")
    private List<CandidatesJobs> candidatesJobs;
    
    @OneToMany(mappedBy = "candidate")
    private List<CandidateSkills> candidatesSkills;
    
    @OneToMany(mappedBy = "candidate")
    private List<Educations> educations;
    
    @OneToMany(mappedBy = "candidate")
    private List<Experiences> experiances;
    
    @OneToMany(mappedBy = "candidate")
    private List<Projects> projects;
    */
}
