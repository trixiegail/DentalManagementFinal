package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.CompletedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;
import com.dentalmanagement.DentalManagement.Service.CompletedAppointmentService;
import com.dentalmanagement.DentalManagement.Service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/completed-appointments")
@CrossOrigin(origins = "https://projectyey.vercel.app")
public class CompletedAppointmentController {

	@Autowired
    private CompletedAppointmentService  completedAppointmentService;
    
    @Autowired
    private PatientRepository patientRepository;

    /**
     * Get all declined appointments
     */
    @GetMapping
    public ResponseEntity<List<CompletedAppointment>> getAllCompletedAppointments() {
        List<CompletedAppointment> completedAppointments = completedAppointmentService.getAllCompletedAppointments();
        return ResponseEntity.ok(completedAppointments);
    }

    /**
     * Delete a specific declined appointment by ID
     */
    @PostMapping("/move/{id}")
    public ResponseEntity<?> moveToCompletedAppointments(@PathVariable Long id) {
        System.out.println("moveToCompletedAppointments endpoint hit for ID: " + id);
        try {
            // Use findById instead of getPatient
            Patient patient = patientRepository.findById(id).orElse(null);

            if (patient != null) {
                completedAppointmentService.moveToCompletedAppointments(patient);
                return ResponseEntity.ok("Moved to Completed Appointments successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Better to log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error moving to Completed Appointments");
        }
    }


}
