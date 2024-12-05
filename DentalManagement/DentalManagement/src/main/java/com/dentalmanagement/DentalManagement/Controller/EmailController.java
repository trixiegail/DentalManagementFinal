package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        sendEmailService.sendEmail("dentalcapstone5@outlook.com", "This is a test email", "Test Email");
        return "Email sent successfully";
    }



    @PostMapping("/notify-approval")
    public String notifyApproval(@RequestParam String email) {
        sendEmailService.sendEmail(email, "Approval Notification", "You have been approved for your requested checkup.");
        return "Approval email sent successfully";
    }

    @PostMapping("/notify-rejection")
    public String notifyRejection(@RequestParam String email) {
        sendEmailService.sendEmail(email, "Rejection Notification", "We regret to inform you that your request has been declined.");
        return "Rejection email sent successfully";
    }
}
