package com.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Entity.Message;


public interface MessageRepository extends JpaRepository<Message, Long>{
	List<Message> findByViewTrue();
	List<Message> findByRecipient(String recipient);
}
