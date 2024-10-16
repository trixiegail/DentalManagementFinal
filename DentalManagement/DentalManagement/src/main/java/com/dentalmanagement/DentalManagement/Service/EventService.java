package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.DTO.ReservationRequest;
import com.dentalmanagement.DentalManagement.Entity.Event;
import com.dentalmanagement.DentalManagement.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElse(null);
    }

    // Fetch available slots for a given date
    public List<Event> getAvailableSlots(String date) {
        return eventRepository.findSlotsByDate(date);
    }

    // Reserve a slot
    public boolean reserveSlot(ReservationRequest request) {
        Optional<Event> event = eventRepository.findByDateAndTime(request.getDate(), request.getTime());
        if (event.isPresent() && event.get().getCount() > 0) {
            event.get().setCount(event.get().getCount() - 1);
            eventRepository.save(event.get());
            return true;
        }
        return false;
    }


    // Other methods like updateEvent, deleteEvent if necessary
}
