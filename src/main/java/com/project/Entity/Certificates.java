package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Certificates {
	@Id
    @Column(name = "links_id")
    private Long linkId;
	
    @Column
    private String name;
    
    @OneToOne
    @JoinColumn(name = "links_id", referencedColumnName = "id")
    private Links link;

    public Certificates(Long linkId, String name, Links link) {
    	this.linkId = linkId;
        this.name = name;
        this.link = link;
    }

    public Certificates() {
    }
    
    public Long getId() {
        return linkId;
    }

    public void setId(Long id) {
        this.linkId = id;
    }

    public String getName() {
        return name;
    }
    
    public Links getLink() {
    	return link;
    }
    
    public void setLink(Links link) {
    	this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }
}
