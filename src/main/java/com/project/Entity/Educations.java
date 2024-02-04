package com.project.Entity;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Educations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long linkId;
	
	@Column
    private String name;
    @Column
    private String school;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "links_id", referencedColumnName = "id") 
    private Links link;

    public Educations(Long linkId, String name, String school, LocalDate startDate, LocalDate endDate, Links link) {
    	this.linkId = linkId;
    	this.name = name;
        this.school = school;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
    }

    public Educations() {
    }
    
    public Long getId() {
        return linkId;
    }

    public void setId(Long id) {
        this.linkId = id;
    }

    public String getName() {
    	return this.name;
    }
    
    public String getSchool() {
        return school;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public Links getLink() {
    	return link;
    }
    
    public void setLink(Links link) {
    	this.link = link;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
