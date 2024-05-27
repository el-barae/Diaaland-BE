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
public class CandidateSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private int score;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skills skill;

    public CandidateSkills(Long id,int score, Candidates candidate, Skills skill) {
        this.id = id;
        this.score = score;
        this.candidate = candidate;
        this.skill = skill;
    }

    public CandidateSkills() {
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
    
    public Candidates getCandidate() {
    	return candidate;
    }
    
    public Skills getSkill() {
        return skill;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCandidate(Candidates candidate ) {
        this.candidate = candidate;
    }

    public void setSkill(Skills s) {
        this.skill = s;
    }
}
