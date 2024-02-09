package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Certificates extends Links{
	
    @Column
    private String domain;
    
    /*@OneToOne
    @JoinColumn(name = "links_id", referencedColumnName = "id")
    private Links link;*/

    public Certificates(String name, String url, String description, Candidates candidate, String domain) {
    	super(name, url, description, candidate);
        this.domain = domain;
    }

    public Certificates() {
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
