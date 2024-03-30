package com.project.model;

public class EmailRequest {

    private String subject;
    private String message;

    // Constructors, getters, and setters

    public EmailRequest() {
    }

    public EmailRequest(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    // Getters and setters

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
