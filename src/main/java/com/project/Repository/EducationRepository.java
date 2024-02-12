package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Educations;

public interface EducationRepository extends JpaRepository<Educations, Long> {
	List<Educations> findByCandidateId(Long candidateId);
}