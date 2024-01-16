package com.project.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Favoris {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Jobs job;

    public Favoris(Long id, Candidates candidate_id, Jobs job_id) {
        this.id = id;
        this.candidate = candidate_id;
        this.job = job_id;
    }

    public Favoris() {
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Candidates getCandidate() {
    	return candidate;
    }
    
    public Jobs getJob() {
        return job;
    }
}