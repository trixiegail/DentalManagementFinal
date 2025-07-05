package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.Email;
import com.dentalmanagement.DentalManagement.Service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailController {
    @Autowired
    private SendEmailService sendEmailService;

//    @GetMapping("/sendEmail")
//    public String sendEmail(){
//        sendEmailService.sendEmail("dentalcapstone5@gmail.com", "This is a test email", "Test Email");
//        return "Email Sent";
//    }

        @PostMapping("/send-email/{email}")
    public String sendMail(@PathVariable String email, @RequestBody Email emailBody) {
        if (email == null || email.isEmpty()) {
            return "Invalid email address";
        }
        if (emailBody == null || emailBody.getSubject() == null || emailBody.getMessage() == null) {
            return "Invalid email body";
        }
        try {
            sendEmailService.sendMail(email, emailBody);
            return "Email sent successfully";
        } catch (Exception e) {
            e.printStackTrace(); // Logs the full exception to the console
            return "Failed to send email: " + e.getMessage(); // Includes exception message in the response
        }
    }

}


//import com.dentalmanagement.DentalManagement.Entity.Email;
//import com.dentalmanagement.DentalManagement.Service.SendEmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/email")
//@CrossOrigin(origins = "http://localhost:5173")
//public class EmailController {
//    @Autowired
//    private SendEmailService sendEmailService;
//
//    @PostMapping("/send-email/{email}")
//    public ResponseEntity<?> sendMail(@PathVariable String email, @RequestBody Email emailBody) {
//        // Validate email
//        if (email == null || email.isEmpty()) {
//            return ResponseEntity.badRequest().body("Invalid email address");
//        }
//
//        // Validate email body
//        if (emailBody == null || emailBody.getSubject() == null || emailBody.getMessage() == null) {
//            return ResponseEntity.badRequest().body("Invalid email body");
//        }
//
//        try {
//            // Log the email details for debugging
//            System.out.println("Sending email to: " + email);
//            System.out.println("Subject: " + emailBody.getSubject());
//            System.out.println("Message: " + emailBody.getMessage());
//
//            // Send the email
//            sendEmailService.sendMail(email, emailBody);
//            return ResponseEntity.ok("Email sent successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return a more detailed error response
//            return ResponseEntity.internalServerError()
//                    .body("Failed to send email: " + e.getMessage());
//        }
//    }
//}

