package com.project.Repository;

import com.project.Entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Matching findByJobIdAndCandidateId(Long jobId, Long candidateId);
}
