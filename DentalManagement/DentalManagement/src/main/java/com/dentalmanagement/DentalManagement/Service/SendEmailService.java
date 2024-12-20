package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.Email;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

//    @Value("${spring.mail.username}")
//    private String fromEmailId;
//
//    public void sendEmail(String recipient, String body, String subject) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom(fromEmailId);
//        simpleMailMessage.setTo(recipient);
//        simpleMailMessage.setText(body);
//        simpleMailMessage.setSubject(subject);
//
//        javaMailSender.send(simpleMailMessage);
//    }

    public void sendMail(String recipient, Email emailBody) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,  "UTF-8");

            // Set the sender email and name
            helper.setFrom("dentalcapstone5@gmail.com", "Dental Management"); // Set your sender's email and display name
            helper.setTo(recipient);
            helper.setSubject(emailBody.getSubject());
            helper.setText(emailBody.getMessage(), true);

            // Actually send the email
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }

        }

}
