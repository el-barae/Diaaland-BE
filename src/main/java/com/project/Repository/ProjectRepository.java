package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Projects;


public interface ProjectRepository extends JpaRepository<Projects, Long> {
	List<Projects> findByCandidateId(Long candidateId);
}