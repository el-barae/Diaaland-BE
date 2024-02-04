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
import jakarta.persistence.OneToOne;

@Entity
public class Links {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String url;
    @Column
    private String description;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    @OneToOne(mappedBy = "link")
    private Educations education;
    
    @OneToOne(mappedBy = "link")
    private Certificates certificate;
    

    public Links(Long id, String url, String desc, Educations e, Certificates c,Candidates candidate) {
    	this.id = id;
        this.url = url;
        this.description=desc;
        this.education = e;
        this.certificate = c;
        this.candidate = candidate;
    }

    public Links() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Educations getEducation() {
        return education;
    }
    
    public Certificates getCertificate() {
        return certificate;
    }
    
    public Candidates getCandidate() {
        return candidate;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
