package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Favoris;

public interface FavorisRepository extends JpaRepository<Favoris, Long> {
}
