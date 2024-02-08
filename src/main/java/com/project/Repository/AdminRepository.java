package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	@Query("SELECT COUNT(*) FROM Jobs")
	int countJobs();
	@Query("SELECT COUNT(*) FROM Candidates")
	int countCandidates();
	@Query("SELECT COUNT(*) FROM Customers")
	int countCustomers();
}
