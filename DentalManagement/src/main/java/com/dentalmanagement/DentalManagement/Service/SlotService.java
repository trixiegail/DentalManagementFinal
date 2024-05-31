package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.Slot;
import com.dentalmanagement.DentalManagement.Repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    public Slot createSlot(Slot slot) {
        slot.setAvailable(true);
        return slotRepository.save(slot);
    }

    public List<Slot> getSlotsByDate(LocalDate date) {
        return slotRepository.findByDate(date);
    }

    public Slot updateSlot(Long id, Slot slotDetails) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Slot with ID " + id + " does not exist"));

        slot.setDate(slotDetails.getDate());
        slot.setStartTime(slotDetails.getStartTime());
        slot.setEndTime(slotDetails.getEndTime());
        slot.setAvailableSlots(slotDetails.getAvailableSlots());
        slot.setAvailable(slotDetails.isAvailable());

        return slotRepository.save(slot);
    }

    public void deleteSlot(Long id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Slot with ID " + id + " does not exist"));
        slotRepository.delete(slot);
    }
}