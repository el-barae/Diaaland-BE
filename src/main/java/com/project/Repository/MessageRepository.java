package com.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Message;


public interface MessageRepository extends JpaRepository<Message, Long>{

}
