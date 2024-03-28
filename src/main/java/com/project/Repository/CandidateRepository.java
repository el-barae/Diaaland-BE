package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Candidates;
import com.project.Entity.User;

public interface CandidateRepository extends JpaRepository<Candidates, Long> {
	Candidates findByEmail(String email);
	Candidates findByUser(User user);
}