package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.Email;
import com.dentalmanagement.DentalManagement.Service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "https://projectyey.vercel.app/")
public class EmailController {

    @Autowired
    private SendEmailService sendEmailService;

    // Endpoint for sending email
//    @GetMapping("/send-email")
//    public String sendEmail() {
//        sendEmailService.sendEmail("", "This is a test email", "Test Email");
//        return "Email sent successfully";
//    }

    @PostMapping("/send-email/{email}")
    public String sendMail(@PathVariable String email, @RequestBody Email emailBody){
        if (email == null || email.isEmpty()) {
            return "Invalid email address";
        }
        if (emailBody == null || emailBody.getSubject() == null || emailBody.getMessage() == null) {
            return "Invalid email body";
        }
        System.out.println("Email: " + email);
        System.out.println("Subject: " + emailBody.getSubject());
        System.out.println("Message: " + emailBody.getMessage());
        sendEmailService.sendMail(email, emailBody);
        return "Email sent successfully";
    }



//    @PostMapping("/notify-approval")
//    public String notifyApproval(@RequestParam String email) {
//        sendEmailService.sendEmail(email, "Approval Notification", "You have been approved for your requested checkup.");
//        return "Approval email sent successfully";
//    }
//
//    @PostMapping("/notify-rejection")
//    public String notifyRejection(@RequestParam String email) {
//        sendEmailService.sendEmail(email, "Rejection Notification", "We regret to inform you that your request has been declined.");
//        return "Rejection email sent successfully";
//    }
}
