package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Entity.Slot;
import com.dentalmanagement.DentalManagement.Repository.ReservationRepository;
import com.dentalmanagement.DentalManagement.Repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SlotRepository slotRepository;

    public Reservation createReservation(Long slotId, Reservation reservationDetails) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new NoSuchElementException("Slot with ID " + slotId + " does not exist"));

        if (slot.getAvailableSlots() <= 0 || !slot.isAvailable()) {
            throw new IllegalStateException("Slot is not available");
        }

        reservationDetails.setSlot(slot);
        reservationDetails.setDate(slot.getDate());
        reservationDetails.setStartTime(slot.getStartTime());
        reservationDetails.setEndTime(slot.getEndTime());

        slot.setAvailableSlots(slot.getAvailableSlots() - 1);
        slotRepository.save(slot);

        return reservationRepository.save(reservationDetails);
    }

    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation with ID " + id + " does not exist"));
    }

    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation with ID " + id + " does not exist"));

        Slot slot = reservation.getSlot();
        slot.setAvailableSlots(slot.getAvailableSlots() + 1);
        slotRepository.save(slot);

        reservationRepository.delete(reservation);
    }
}