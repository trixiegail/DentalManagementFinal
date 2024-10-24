package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.DeclinedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Service.DeclinedAppointmentService;
import com.dentalmanagement.DentalManagement.Service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/declined-appointments")
@CrossOrigin(origins = "http://localhost:5173")  // Adjust CORS policy if needed
public class DeclinedAppointmentController {

    @Autowired
    private DeclinedAppointmentService declinedAppointmentService;
    
    @Autowired
    private ReservationService reservationService; 

    /**
     * Get all declined appointments
     */
    @GetMapping
    public ResponseEntity<List<DeclinedAppointment>> getAllDeclinedAppointments() {
        List<DeclinedAppointment> declinedAppointments = declinedAppointmentService.getAllDeclinedAppointments();
        return ResponseEntity.ok(declinedAppointments);
    }

    /**
     * Delete a specific declined appointment by ID
     */
    @PostMapping("/move/{id}")
    public ResponseEntity<?> moveToDeclinedAppointments(@PathVariable Long id) {
        System.out.println("moveToDeclinedAppointments endpoint hit for ID: " + id);
        try {
            Reservation reservation = reservationService.getReservation(id);
            if (reservation != null) {
                declinedAppointmentService.moveToDeclinedAppointments(reservation);
                return ResponseEntity.ok("Moved to Declined Appointments successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error moving to Declined Appointments");
        }
    }
    
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptReservation(@PathVariable("id") Long id) {
        try {
            boolean success = reservationService.acceptReservation(id);
            if (success) {
                return ResponseEntity.ok("Reservation accepted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting reservation");
        }
    }


}
