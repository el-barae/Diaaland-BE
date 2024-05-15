package com.project.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
	    
	    public boolean areAnyMessagesNotViewed(String recepient) {
	        List<Message> messages = messageRepository.findByRecipient(recepient);
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

		public List<Message> getMessageByRecipient(String recipient) {
		return messageRepository.findByRecipient(recipient);
	}

	    public Message saveMessage(Message message) {
			LocalDateTime nowWithoutMillis = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
			message.setDate(nowWithoutMillis);
	        return messageRepository.save(message);
	    }
	    
	    @Transactional
		public void setAllMessagesViewed(String recipient) {
			List<Message> messages = messageRepository.findByRecipient(recipient);
			for (Message message : messages) {
				message.setView(true);
				messageRepository.save(message);
			}
		}

	    public void deleteMessage(Long id) {
	        messageRepository.deleteById(id);
	    }

	@Transactional
	public void deleteMessagesByRecipient(String recipient) {
		List<Message> messages = messageRepository.findByRecipient(recipient);
		messageRepository.deleteAll(messages);
	}

}
