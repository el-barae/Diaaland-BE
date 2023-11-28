package com.project.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.CandidatesJobs;


public interface CandidateJobRepository extends JpaRepository<CandidatesJobs, Long> {
}