package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Links;

public interface LinkRepository extends JpaRepository<Links, Long> {
	List<Links> findByCandidateId(Long candidateId);
}