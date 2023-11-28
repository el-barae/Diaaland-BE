package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Candidates;




public interface CandidateRepository extends JpaRepository<Candidates, Long> {
}