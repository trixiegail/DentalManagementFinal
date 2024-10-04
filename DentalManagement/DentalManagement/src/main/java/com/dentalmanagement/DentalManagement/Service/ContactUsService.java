package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.ContactUs;
import com.dentalmanagement.DentalManagement.Repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public ContactUs saveContactUs(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    public List<ContactUs> getAllContacts() {
        return contactUsRepository.findAll();
    }

    public ContactUs getContactById(Long id) {
        return contactUsRepository.findById(id).orElse(null);
    }
    
    public void deleteContact(Long id) {
        contactUsRepository.deleteById(id);
    }
}

