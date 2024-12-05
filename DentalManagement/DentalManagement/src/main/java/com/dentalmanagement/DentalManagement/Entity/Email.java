package com.dentalmanagement.DentalManagement.Entity;

public class Email {
    private String email;
    private String subject;
    private String message;

    public Email() {
    }

    public Email(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String body) {
        this.message = body;
    }
}
