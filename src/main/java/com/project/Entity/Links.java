package com.project.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Links {
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
	@Column
    private String name;
    @Column
    private String url;
    @Column
    private String description;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    

    public Links(String name, String url, String desc, Candidates candidate) {
    	this.name = name;
        this.url = url;
        this.description=desc;
        this.candidate = candidate;
    }

    public Links() {
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Candidates getCandidate() {
        return candidate;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCandidate(Candidates candidate) {
        this.candidate = candidate;
    }
}
