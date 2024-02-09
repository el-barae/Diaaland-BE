package com.project.Entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Educations extends Links{
    @Column
    private String school;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;

   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "links_id", referencedColumnName = "id") 
    private Links link;*/

    public Educations(String name, String url, String description, Candidates candidate, String school, LocalDate startDate, LocalDate endDate) {
        super(name, url, description, candidate);
        this.school = school;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Educations() {
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
