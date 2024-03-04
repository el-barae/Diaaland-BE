package com.project.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Candidates;

public interface CandidateRepository extends JpaRepository<Candidates, Long> {
	Optional<Candidates> findByEmail(String email);
}