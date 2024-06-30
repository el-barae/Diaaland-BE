package com.project.Repository;

import com.project.Entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Matching findByJobIdAndCandidateId(Long jobId, Long candidateId);

    @Query("SELECT m FROM Matching m WHERE m.candidate.id = :candidateId")
    List<Matching> findMatchingByCandidateId(@Param("candidateId") Long candidateId);

    @Query("SELECT m FROM Matching m WHERE m.job.customer.id = :customerId")
    List<Matching> findMatchingByCustomerId(@Param("customerId") Long customerId);
}
