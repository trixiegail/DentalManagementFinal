package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.DTO.ReservationRequest;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Service.ReservationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:5173/")  // Adjust CORS as necessary
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * Create reservation with the given slot ID
     */
    @PostMapping("/{slotId}")
    public ResponseEntity<?> createReservation(@PathVariable Long slotId, @RequestBody Reservation reservationDetails) {
        try {
            Reservation createdReservation = reservationService.createReservation(slotId, reservationDetails);
            return ResponseEntity.ok(createdReservation);
        } catch (NoSuchElementException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Reservation with ID " + id + " does not exist");
        }
    }


    /**
     * Reserve a slot based on ReservationRequest (simplified endpoint)
     */
    @PostMapping("/reserve")
    public ResponseEntity<Map<String, String>> reserveSlot(@RequestBody ReservationRequest request) {
        boolean success = reservationService.reserveSlot(request);
        Map<String, String> response = new HashMap<>();
        
        try {
        if (success) {
            response.put("message", "Slot reserved successfully");
            return ResponseEntity.ok(response);  // Return JSON response
        } else {
            response.put("error", "Failed to reserve slot");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // Return JSON error response
        }
    } catch (IllegalStateException e) {
        // Return error if student already has a reservation
        response.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (NoSuchElementException e) {
        response.put("error", "The event does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
    
    
    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") Long id) { // Explicitly name the PathVariable as "id"
        try {
            boolean success = reservationService.cancelReservation(id);
            if (success) {
                return ResponseEntity.ok("Reservation cancelled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error cancelling reservation");
        }
    }
    
//    @PutMapping("/accept/{id}")
//    public ResponseEntity<?> acceptReservation(@PathVariable Long id) {
//        try {
//            boolean success = reservationService.acceptReservation(id);
//            if (success) {
//                return ResponseEntity.ok("Reservation accepted successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting reservation");
//        }
//    }
    
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
