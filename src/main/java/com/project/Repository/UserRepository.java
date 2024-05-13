package com.project.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {  
  Optional<User> findByEmail(String email);
}
