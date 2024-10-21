package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByDate(String date);
	boolean existsByStudentIdNumber(String studentIdNumber);
	List<Reservation> findByStudentIdNumber(String studentIdNumber);
}


