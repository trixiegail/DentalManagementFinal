package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.ContactUs;
import com.dentalmanagement.DentalManagement.Service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping
    public ResponseEntity<ContactUs> createContact(@RequestBody ContactUs contactUs) {
        ContactUs savedContact = contactUsService.saveContactUs(contactUs);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContactUs>> getAllContacts() {
        List<ContactUs> contacts = contactUsService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUs> getContactById(@PathVariable Long id) {
        ContactUs contact = contactUsService.getContactById(id);
        if (contact != null) {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactUsService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

