package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.Entity.Slot;
import com.dentalmanagement.DentalManagement.Service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin(origins = "http://localhost:5173/")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping
    public ResponseEntity<?> createSlot(@RequestBody Slot slot) {
        try {
            Slot createdSlot = slotService.createSlot(slot);
            return ResponseEntity.ok(createdSlot);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating slot");
        }
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Slot>> getSlotsByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Slot> slots = slotService.getSlotsByDate(localDate);
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlot(@PathVariable Long id, @RequestBody Slot slotDetails) {
        try {
            Slot updatedSlot = slotService.updateSlot(id, slotDetails);
            return ResponseEntity.ok(updatedSlot);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Slot with ID " + id + " does not exist");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlot(@PathVariable Long id) {
        try {
            slotService.deleteSlot(id);
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Slot with ID " + id + " does not exist");
        }
    }
}
