package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "https://projectyey.vercel.app/")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long id) {  // Explicitly name the parameter
        try {
            patientRepository.deleteById(id);  // Assuming you're using a repository method for deletion
            return ResponseEntity.ok().body("Patient Done");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
    }


}
