package com.project.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Experiences;


public interface ExperianceRepository extends JpaRepository<Experiences, Long> {
	List<Experiences> findByCandidateId(Long candidateId);
}