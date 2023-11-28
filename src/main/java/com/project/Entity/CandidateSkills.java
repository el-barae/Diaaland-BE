package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CandidateSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private int score;

    public CandidateSkills(Long id,int score) {
        this.id = id;
        this.score = score;
    }

    public CandidateSkills() {
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skills skill;
}
