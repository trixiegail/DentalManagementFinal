package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Integer>{
	DoctorEntity findByIdNumberAndPassword(String idNumber, String password);
	// Find students by archived status
	List<DoctorEntity> findByArchived(boolean archived);

	// Search for students by first name, last name, or ID number
	List<DoctorEntity> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(String firstName, String lastName, String idNumber);

	//Find archive doctor accounts
	List<DoctorEntity> findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
			boolean archived, String firstName, String lastName, String idNumber);

	List<DoctorEntity> findByEmail(String email);
	List<DoctorEntity> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
}
