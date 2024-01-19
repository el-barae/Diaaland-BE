package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.Entity.Favoris;
import com.project.Entity.Jobs;

public interface FavorisRepository extends JpaRepository<Favoris, Long> {
	@Query("SELECT f.job FROM Favoris f WHERE f.candidate.id = :candidateId")
    List<Jobs> findJobsByCandidateId(@Param("candidateId") Long candidateId);
}
