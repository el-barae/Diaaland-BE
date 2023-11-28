package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.CandidateSkills;


public interface CandidateSkillRepository extends JpaRepository<CandidateSkills, Long> {
}