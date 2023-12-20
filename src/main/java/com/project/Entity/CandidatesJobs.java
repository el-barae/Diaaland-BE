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
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Jobs job;

    public CandidatesJobs(Long id, String status, Candidates candidate_id, Jobs job_id) {
        this.id = id;
        this.status = status;
        this.candidate = candidate_id;
        this.job = job_id;
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
}