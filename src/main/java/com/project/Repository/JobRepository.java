package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Customers;
import com.project.Entity.Jobs;

public interface JobRepository extends JpaRepository<Jobs, Long> {
	List<Jobs> findByCustomer(Customers customer);
}