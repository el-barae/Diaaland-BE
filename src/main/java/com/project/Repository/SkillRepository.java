package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Skills;


public interface SkillRepository extends JpaRepository<Skills, Long> {
}