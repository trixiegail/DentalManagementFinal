package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.Patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findByDate(String date);
	boolean existsByStudentIdNumber(String studentIdNumber);
	List<Patient> findByStudentIdNumber(String studentIdNumber);
}
