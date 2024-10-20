package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.DTO.ReservationRequest;
import com.dentalmanagement.DentalManagement.Entity.Event;
import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Repository.ReservationRepository;
import com.dentalmanagement.DentalManagement.Repository.EventRepository;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    /**
     * Create a new reservation for a specific slot (Event)
     */
    public Reservation createReservation(Long slotId, Reservation reservationDetails) {
        // Find the slot (Event) by ID
        Optional<Event> event = eventRepository.findById(slotId);

        if (event.isPresent()) {
            // Check if the slot has available capacity
            Event foundEvent = event.get();
            if (foundEvent.getCount() > 0) {
                // Reduce the available slots count for the event
                foundEvent.setCount(foundEvent.getCount() - 1);
                
                // If no slots remain, mark the event as Unavailable
                if (foundEvent.getCount() == 0) {
                    foundEvent.setType("Unavailable");
                }
                
                eventRepository.save(foundEvent); // Save the updated event

                // Set the event details for the reservation and save it
                reservationDetails.setDate(foundEvent.getDate());
                reservationDetails.setTime(foundEvent.getTime());
                Reservation savedReservation = reservationRepository.save(reservationDetails);

                return savedReservation;
            } else {
                throw new IllegalStateException("No available slots for this event.");
            }
        } else {
            throw new NoSuchElementException("Event with ID " + slotId + " not found.");
        }
    }

    /**
     * Retrieve a reservation by its ID
     */
    public Reservation getReservation(Long id) {
        // Find the reservation by ID
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new NoSuchElementException("Reservation with ID " + id + " does not exist.");
        }
    }
    
    public boolean reserveSlot(ReservationRequest request) {
    	
    	// Check if student has an existing reservation
        boolean hasExistingReservation = reservationRepository.existsByStudentIdNumber(request.getStudentIdNumber());

        if (hasExistingReservation) {
            throw new IllegalStateException("You already have a reservation.");
        }
        
       
        Optional<Event> event = eventRepository.findByDateAndTime(request.getDate(), request.getTime());
        if (event.isPresent()) {
            Event foundEvent = event.get();
            
        if (event.isPresent() && event.get().getCount() > 0) {
        	
        	event.get().setCount(event.get().getCount() - 1);
        	
        	// If no slots remain, mark the event as Unavailable
            if (foundEvent.getCount() == 0) {
                foundEvent.setType("Unavailable");
            }
        	
            eventRepository.save(foundEvent);

            
            // Create and save the reservation
            Reservation reservation = new Reservation();
            reservation.setDate(request.getDate());
            reservation.setTime(request.getTime());
            reservation.setStudentIdNumber(request.getStudentIdNumber());
            reservation.setFullName(request.getFullName());
            reservation.setProgram(request.getProgram());
            reservation.setYearLevel(request.getYearLevel()); 
            reservation.setEvent(foundEvent);

            reservationRepository.save(reservation);
            return true;
        }      else {
        throw new IllegalStateException("No available slots for this event.");
    }
}

return false;
}

	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}
	
	 /**
     * Get reservations by a specific date (Optional)
     */
    public List<Reservation> getReservationsByDate(String date) {
        return reservationRepository.findByDate(date);
    }

    /**
     * Cancel a reservation and update the event (if needed)
     */
    public boolean cancelReservation(Long reservationId) {
        try {
            Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
            if (reservationOpt.isPresent()) {
                Reservation reservation = reservationOpt.get();
                
                // Find the event associated with this reservation
                Optional<Event> eventOpt = eventRepository.findById(reservation.getEvent().getId());
                if (eventOpt.isPresent()) {
                    Event event = eventOpt.get();
                    
                    // Increment the available slots
                    event.setCount(event.getCount() + 1);
                    
                    // Update event status if needed
                    if (event.getCount() > 0 && event.getType().equals("Unavailable")) {
                        event.setType("Available");
                    }
                    
                    // Save the updated event
                    eventRepository.save(event);
                } else {
                    System.out.println("No associated event found for the reservation.");
                }
                
                // Now delete the reservation
                reservationRepository.deleteById(reservationId);
                return true;
            } else {
                System.out.println("No reservation found with ID: " + reservationId);
                return false;
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.out.println("Error cancelling reservation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean acceptReservation(Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            Patient patient = new Patient();

            // Transfer reservation details to patient
            patient.setFullName(reservation.getFullName());
            patient.setStudentIdNumber(reservation.getStudentIdNumber());
            patient.setProgram(reservation.getProgram());
            patient.setYearLevel(reservation.getYearLevel());
            patient.setDate(reservation.getDate()); 
            patient.setTime(reservation.getTime());

            // Save the patient to the patient repository
            patientRepository.save(patient);

            // Delete the reservation from the repository
            reservationRepository.deleteById(id);

            return true;
        } else {
            throw new NoSuchElementException("Reservation not found");
        }
    }


    
    


    
    
}
