package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "https://projectyey.vercel.app/")
public class EmailController {

    @Autowired
    private SendEmailService sendEmailService;

    // Endpoint for sending email
    @GetMapping("/send-email")
    public String sendEmail() {
        sendEmailService.sendEmail("dentalcapstone5@gmail.com", "This is a test email", "Test Email");
        return "Email sent successfully";
    }
}