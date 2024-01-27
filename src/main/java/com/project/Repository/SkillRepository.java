package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.Entity.Skills;


public interface SkillRepository extends JpaRepository<Skills, Long> {
	@Query("SELECT s.id FROM Skills s WHERE s.name = :name")
	public Long findIdByName(String name);
}