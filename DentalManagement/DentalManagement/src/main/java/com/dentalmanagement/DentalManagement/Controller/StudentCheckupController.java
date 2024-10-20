package com.dentalmanagement.DentalManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dentalmanagement.DentalManagement.Entity.StudentCheckup;
import com.dentalmanagement.DentalManagement.Service.StudentCheckupService;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/checkups")
public class StudentCheckupController {

    @Autowired
    private StudentCheckupService studentCheckupService;

    @GetMapping
    public List<StudentCheckup> getAllCheckups() {
        return studentCheckupService.getAllCheckups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCheckup> getCheckupById(@PathVariable Long id) {
        Optional<StudentCheckup> checkup = studentCheckupService.getCheckupById(id);
        return checkup.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentCheckup createCheckup(@RequestBody StudentCheckup checkup) {
        return studentCheckupService.createOrUpdateCheckup(checkup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCheckup> updateCheckup(@PathVariable Long id, @RequestBody StudentCheckup checkupDetails) {
        Optional<StudentCheckup> existingCheckup = studentCheckupService.getCheckupById(id);
        if (existingCheckup.isPresent()) {
            StudentCheckup checkup = existingCheckup.get();
            checkup.setBloodPressure(checkupDetails.getBloodPressure());
            checkup.setHeartRate(checkupDetails.getHeartRate());
            checkup.setRespiratoryRate(checkupDetails.getRespiratoryRate());
            checkup.setTemperature(checkupDetails.getTemperature());
            checkup.setOralHealthStatus(checkupDetails.getOralHealthStatus());
            checkup.setGumHealth(checkupDetails.getGumHealth());
            checkup.setPresenceOfCavities(checkupDetails.getPresenceOfCavities());
            checkup.setGeneralHealthCondition(checkupDetails.getGeneralHealthCondition());
            checkup.setSpecificHealthConcerns(checkupDetails.getSpecificHealthConcerns());

            return ResponseEntity.ok(studentCheckupService.createOrUpdateCheckup(checkup));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckup(@PathVariable Long id) {
        studentCheckupService.deleteCheckup(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping("/save")
    public ResponseEntity<StudentCheckup> saveCheckup(@RequestBody StudentCheckup checkup, @RequestParam String idNumber) {
        // Get the current time in the desired timezone
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Manila")); // Adjust for your timezone
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime(); // Convert to LocalDateTime

        // Set the local time in the checkup object
        checkup.setDate(localDateTime);

        // Call the service method to save the checkup
        StudentCheckup savedCheckup = studentCheckupService.saveCheckup(idNumber, checkup);
        return ResponseEntity.ok(savedCheckup);
    }

    
    @GetMapping("/student/{idNumber}")
    public ResponseEntity<List<StudentCheckup>> getCheckupsByStudentIdNumber(@PathVariable String idNumber) {
        List<StudentCheckup> checkups = studentCheckupService.getCheckupsByStudentIdNumber(idNumber);
        return ResponseEntity.ok(checkups); // Ensure this is returning a list
    }



}
