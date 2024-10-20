package com.dentalmanagement.DentalManagement.Controller;

import com.dentalmanagement.DentalManagement.DTO.ReservationRequest;
import com.dentalmanagement.DentalManagement.Entity.Event;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Service.EventService;
import com.dentalmanagement.DentalManagement.Service.ReservationService;
import com.dentalmanagement.DentalManagement.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReservationService reservationService;



    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }


    @PutMapping("/book/{id}")
    public Event bookEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null && !event.getIsBooked()) {
            event.setIsBooked(true);
            return eventService.saveEvent(event);
        }
        return null; // Handle error cases appropriately
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }

    @PostMapping("/events")
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/available")
    public List<Event> getAvailableSlots(@RequestParam String date) {
        return eventService.getAvailableSlots(date);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveSlot(@RequestBody ReservationRequest request) {
        boolean success = reservationService.reserveSlot(request);
        if (success) {
            return ResponseEntity.ok("Slot reserved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reserve slot");
        }
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
	    



    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event updatedEvent = existingEvent.get();
            updatedEvent.setTitle(event.getTitle());
            updatedEvent.setStart(event.getStart());
            updatedEvent.setEnd(event.getEnd());
            updatedEvent.setType(event.getType());
            // Save the updated event back to the database
            eventRepository.save(updatedEvent);
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reserve/{id}")
    public ResponseEntity<Event> reserveEvent(@PathVariable Long id, @RequestBody ReservationRequest request) {
        Event event = eventService.getEventById(id);
        
        if (event != null && !event.getIsBooked()) {
            event.setIsBooked(true); // Mark as reserved
            event.setTitle("Reserved Slot");
            event.setType("Unavailable");
            eventService.saveEvent(event);
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




}