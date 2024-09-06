package com.dentalmanagement.DentalManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dentalmanagement.DentalManagement.Entity.Schedule;
import com.dentalmanagement.DentalManagement.Service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "http://localhost:5173/")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping 
    public ResponseEntity<?> saveSchedule(@RequestBody List<Schedule> scheduleList) {
        try {
            scheduleService.saveSchedules(scheduleList);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-schedule")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        try {
            List<Schedule> schedules = scheduleService.getAllSchedules();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update-slots/{id}")
    public ResponseEntity<Schedule> updateSlots(@PathVariable Long id, @RequestParam Integer newSlots) {
        Schedule updatedSchedule = scheduleService.updateSlots(id, newSlots);
        return ResponseEntity.ok(updatedSchedule);
    }


}

