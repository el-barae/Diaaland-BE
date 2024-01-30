package com.project.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.Entity.Candidates;
import com.project.Entity.CandidatesJobs;
import com.project.Entity.Jobs;


public interface CandidateJobRepository extends JpaRepository<CandidatesJobs, Long> {
	@Query("SELECT cj.job FROM CandidatesJobs cj WHERE cj.candidate.id = :candidateId")
    List<Jobs> findJobsByCandidateId(@Param("candidateId") Long candidateId);
	
	@Query("SELECT cj.candidate FROM CandidatesJobs cj WHERE cj.job.id = :jobId")
    List<Candidates> findCandidatesByJobId(@Param("jobId") Long jobId);
	
	@Query("SELECT cj.candidate FROM CandidatesJobs cj, Jobs j "
	        + "WHERE cj.job.id = j.id "
	        + "AND j.customer.id = :customerId")
	List<Candidates> findCandidatesByCustomerId(@Param("customerId") Long customerId);
}