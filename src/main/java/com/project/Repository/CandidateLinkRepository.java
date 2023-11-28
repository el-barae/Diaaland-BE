package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.CandidateLinks;
import com.project.Entity.Candidates;
import com.project.Entity.Links;



public interface CandidateLinkRepository extends JpaRepository<CandidateLinks, Long> {
	List<CandidateLinks> findByCandidate(Candidates candidate);
    List<CandidateLinks> findByLink(Links link);
}