package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173")
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
