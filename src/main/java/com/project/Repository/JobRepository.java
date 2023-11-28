package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Long> {

}