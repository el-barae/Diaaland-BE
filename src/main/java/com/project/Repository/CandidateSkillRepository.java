package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.Entity.CandidateSkills;
import com.project.Entity.Candidates;
import com.project.Entity.Skills;


public interface CandidateSkillRepository extends JpaRepository<CandidateSkills, Long> {
	@Query("SELECT cs.skill FROM CandidateSkills cs WHERE cs.candidate.id = :candidateId")
    List<Skills> findSkillsByCandidateId(@Param("candidateId") Long candidateId);
	@Query("SELECT cs.candidate FROM CandidateSkills cs WHERE cs.skill.id = :skillId")
    List<Candidates> findCandidatesBySkillId(@Param("skillId") Long skillId);
}