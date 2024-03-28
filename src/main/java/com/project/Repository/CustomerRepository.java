package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.Customers;
import com.project.Entity.User;

public interface CustomerRepository extends JpaRepository<Customers, Long> {
	Customers findByEmail(String email);
	Customers findByUser(User user);
}