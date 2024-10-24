package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.DeclinedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Reservation;

public interface DeclinedAppointmentRepository extends JpaRepository<DeclinedAppointment, Long> {
	boolean existsByStudentIdNumber(String studentIdNumber);
	List<DeclinedAppointment> findByStudentIdNumberAndDateAndTime(String studentIdNumber, String date, String time);
	List<Reservation> findByDate(String date);
	List<Reservation> findByStudentIdNumber(String studentIdNumber);
}
