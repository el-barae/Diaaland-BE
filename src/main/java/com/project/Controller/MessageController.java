package com.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Entity.Message;
import com.project.Service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message messages = messageService.getMessageById(id);
        if (messages != null) {
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipient/{recipient}")
    public ResponseEntity<List<Message>> getMessageByTo(@PathVariable String recipient) {
        List<Message> messages = messageService.getMessageByRecipient(recipient);
        if (messages != null) {
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/viewed/{recipient}")
    public boolean isMessagesNotViewed(@PathVariable String recipient) {
        return messageService.areAnyMessagesNotViewed(recipient);
    }

    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        Message m = messageService.saveMessage(message);
        if (m != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/mark-viewed/{recipient}")
    public void markAllMessagesViewed(@PathVariable String recipient) {
        messageService.setAllMessagesViewed(recipient);
    }

    @DeleteMapping("/{id}")
    public void  deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }
}
