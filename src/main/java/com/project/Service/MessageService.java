package com.project.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Entity.Message;
import com.project.Repository.MessageRepository;

@Service
public class MessageService {
	
	 private final MessageRepository messageRepository;

	    public MessageService(MessageRepository messageRepository) {
	        this.messageRepository = messageRepository;
	    }

	    public List<Message> getAllMessages() {
	    	Sort sort = Sort.by(Sort.Direction.DESC, "date");
	        return messageRepository.findAll(sort);
	    }
	    
	    public boolean areAnyMessagesNotViewed() {
	        List<Message> messages = messageRepository.findAll();
	        for (Message message : messages) {
	            if (!message.getView()) {
	                return true;
	            }
	        }
	        return false; 
	    }
	 
	    public Message getMessageById(Long id) {
	        return messageRepository.findById(id).orElse(null);
	    }

	    public Message saveMessage(Message message) {
	        message.setDate(LocalDateTime.now());
	        return messageRepository.save(message);
	    }
	    
	    @Transactional
	    public void setAllMessagesViewed() {
	        List<Message> allMessages = messageRepository.findAll();
	        for (Message message : allMessages) {
	            message.setView(true);
	        }
	        messageRepository.saveAll(allMessages);
	    }

	    public void deleteMessage(Long id) {
	        messageRepository.deleteById(id);
	    }

}
