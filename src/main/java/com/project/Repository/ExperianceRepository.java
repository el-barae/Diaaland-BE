package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Experiences;


public interface ExperianceRepository extends JpaRepository<Experiences, Long> {
}