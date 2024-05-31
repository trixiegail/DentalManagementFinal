package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Service.ReservationService;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:5173/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok("Reservation cancelled successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Reservation with ID " + id + " does not exist");
        }
    }
}