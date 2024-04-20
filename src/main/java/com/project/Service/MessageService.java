package com.project.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Entity.Message;
import com.project.Repository.MessageRepository;

@Service
public class MessageService {
	
	 private final MessageRepository messageRepository;

	    public MessageService(MessageRepository messageRepository) {
	        this.messageRepository = messageRepository;
	    }

	    public List<Message> getAllMessages() {
	        return messageRepository.findAll();
	    }

	    public Message getMessageById(Long id) {
	        return messageRepository.findById(id).orElse(null);
	    }

	    public Message saveMessage(Message message) {
	        message.setDate(LocalDateTime.now());
	        return messageRepository.save(message);
	    }

	    public void deleteMessage(Long id) {
	        messageRepository.deleteById(id);
	    }

}
