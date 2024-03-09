package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;

@Builder
@Entity
public class Candidates extends User{
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String accountStatus;
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
    
    
    public Candidates() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountStatus() {
        return accountStatus;
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
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
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
    
    public String toString() {
    	return 
        this.firstName +"|~"+
        this.lastName +"|~"+
        /*this.description+"|~"+
        this.email+"|~"+*/
        this.accountStatus+"|~"+
        this.getPhoneNumber() +"|~"+
        this.jobStatus+"|~"+
        this.linkedIn +"|~"+
        this.gitHub +"|~"+
        this.portfolio +"|~"+
        this.blog+"|~"+
        this.expectedSalary +"|~"+
        this.resumeLink+"|~"+
        this.photoLink;
    }

    public Candidates( String email, String password, String phoneNumber, String description, String adress, String city, String country, Role role, String firstName, String lastName, String accountStatus, String jobStatus, String linkedIn, String gitHub, String portfolio, String blog, double expectedSalary, String resumeLink, String photoLink) {
    	super(email, password, phoneNumber, description, adress, city, country, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountStatus = accountStatus;
        this.jobStatus = jobStatus;
        this.linkedIn = linkedIn;
        this.gitHub = gitHub;
        this.portfolio = portfolio;
        this.blog = blog;
        this.expectedSalary = expectedSalary;
        this.resumeLink = resumeLink;
        this.photoLink = photoLink;
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Candidates candidate = new Candidates();

        public Builder email(String email) {
            candidate.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            candidate.setPassword(password);
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            candidate.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder description(String description) {
            candidate.setDescription(description);
            return this;
        }

        public Builder adress(String adress) {
            candidate.setAdress(adress);
            return this;
        }

        public Builder city(String city) {
            candidate.setCity(city);
            return this;
        }

        public Builder country(String country) {
            candidate.setCountry(country);
            return this;
        }

        public Builder role(Role role) {
            candidate.setRole(role);
            return this;
        }

        public Builder firstName(String firstName) {
            candidate.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            candidate.lastName = lastName;
            return this;
        }

        public Builder accountStatus(String accountStatus) {
            candidate.accountStatus = accountStatus;
            return this;
        }

        public Builder jobStatus(String jobStatus) {
            candidate.jobStatus = jobStatus;
            return this;
        }

        public Builder linkedIn(String linkedIn) {
            candidate.linkedIn = linkedIn;
            return this;
        }

        public Builder gitHub(String gitHub) {
            candidate.gitHub = gitHub;
            return this;
        }

        public Builder portfolio(String portfolio) {
            candidate.portfolio = portfolio;
            return this;
        }

        public Builder blog(String blog) {
            candidate.blog = blog;
            return this;
        }

        public Builder expectedSalary(double expectedSalary) {
            candidate.expectedSalary = expectedSalary;
            return this;
        }

        public Builder resumeLink(String resumeLink) {
            candidate.resumeLink = resumeLink;
            return this;
        }

        public Builder photoLink(String photoLink) {
            candidate.photoLink = photoLink;
            return this;
        }

        public Candidates build() {
            return candidate;
        }
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
