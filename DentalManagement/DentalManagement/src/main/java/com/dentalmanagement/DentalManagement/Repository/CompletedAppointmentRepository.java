package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.CompletedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedAppointmentRepository extends JpaRepository<CompletedAppointment, Long> {
	boolean existsByStudentIdNumber(String studentIdNumber);
	List<CompletedAppointment> findByStudentIdNumberAndDateAndTime(String studentIdNumber, String date, String time);
	List<Reservation> findByDate(String date);
	List<Reservation> findByStudentIdNumber(String studentIdNumber);
}
