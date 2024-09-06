package com.dentalmanagement.DentalManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
}
