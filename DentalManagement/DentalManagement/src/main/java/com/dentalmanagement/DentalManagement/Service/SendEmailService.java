package com.dentalmanagement.DentalManagement.Service;


import com.dentalmanagement.DentalManagement.Entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;

//    public void sendEmail(String email, MailS) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom(fromEmailId);
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setText(body);
//        simpleMailMessage.setSubject(subject);
//
//        javaMailSender.send(simpleMailMessage);
//    }
    public void sendMail(String email, Email emailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailId);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(emailBody.getMessage());
        simpleMailMessage.setSubject(emailBody.getSubject());

        javaMailSender.send(simpleMailMessage);
    }

}
