package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Projects;


public interface ProjectRepository extends JpaRepository<Projects, Long> {
}