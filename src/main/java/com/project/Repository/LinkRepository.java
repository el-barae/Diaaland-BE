package com.project.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Links;

public interface LinkRepository extends JpaRepository<Links, Long> {
}