package com.project.Service;

import java.util.List;
import com.project.Entity.Message;
import com.project.Repository.MessageRepository;

public class MessageService {
	
	MessageRepository messageŔepository;
	
	public MessageService(MessageRepository messageŔepository) {
		this.messageŔepository = messageŔepository;
	}
	
	public List<Message> getAllMessage(){
		return messageŔepository.findAll();
	}
	
	public Message createMessage(Message message ) {
		return messageŔepository.save(message);
	}

}
