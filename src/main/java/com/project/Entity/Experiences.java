package com.project.Entity;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Experiences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public Candidates getCandidate() {
    	return candidate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCandidate(Candidates candidate){
    	this.candidate = candidate;
    }

    public Experiences() {
    }

    public Experiences(Long id, String name, LocalDate startDate, LocalDate endDate, Candidates candidate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.candidate = candidate;
    }
   
}
