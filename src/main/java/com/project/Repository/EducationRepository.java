package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Educations;

public interface EducationRepository extends JpaRepository<Educations, Long> {
}