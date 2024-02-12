package com.project.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Other_Links;

public interface Other_linkRepository extends JpaRepository<Other_Links, Long>{
	List<Other_Links> findByCandidateId(Long candidateId);
}
