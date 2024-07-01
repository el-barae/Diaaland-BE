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

    @Query("SELECT m1 FROM Matching m1 WHERE (SELECT COUNT(m2) FROM Matching m2 WHERE m2.candidate.id = m1.candidate.id AND m2.job.id = m1.job.id) > 1")
    List<Matching> findDuplicates();

    // Method to delete all duplicates except one for each candidate-job combination
    void deleteByIdNotIn(List<Long> ids);
}
