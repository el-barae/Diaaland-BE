package com.project.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CandidatesJobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @Column
    private String status;
    
    @Column
    private String cv;
    
    @Column
    private String diploma;
    
    @Column
    private String coverLetter;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Jobs job;

    public CandidatesJobs(Long id, String status, Candidates candidate_id, Jobs job_id, String cv, String diploma, String coverLetter) {
        this.id = id;
        this.status = status;
        this.candidate = candidate_id;
        this.job = job_id;
        this.cv = cv;
        this.diploma = diploma;
        this.coverLetter = coverLetter;
    }

    public CandidatesJobs() {
    }

    public CandidatesJobs(Long id2, String status2) {
    	this.id = id2;
        this.status = status2;
	}

	public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    
    public Candidates getCandidate() {
    	return candidate;
    }
    
    public Jobs getJob() {
        return job;
    }
    
    public void setCandidates(Candidates candidate) {
    	this.candidate = candidate;
    }
    
    public void setJob(Jobs job) {
    	this.job = job;
    }
    
 // Getter and Setter for cv
    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    // Getter and Setter for diploma
    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    // Getter and Setter for coverLetter
    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }
}