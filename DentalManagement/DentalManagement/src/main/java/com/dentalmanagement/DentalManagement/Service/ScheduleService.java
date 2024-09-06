package com.dentalmanagement.DentalManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.Schedule;
import com.dentalmanagement.DentalManagement.Repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void saveSchedules(List<Schedule> scheduleList) {
        scheduleRepository.saveAll(scheduleList);
    }
    
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    
    public Schedule updateSlots(Long id, Integer newSlots) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setSlotsLeft(newSlots);
            return scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
    }

}

