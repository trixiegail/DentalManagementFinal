package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.NurseEntity;


public interface NurseRepository extends JpaRepository<NurseEntity, Integer>{
	NurseEntity findByIdNumberAndPassword(String idNumber, String password);

	// Find students by archived status
	List<NurseEntity> findByArchived(boolean archived);

	// Search for students by first name, last name, or ID number
	List<NurseEntity> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(String firstName, String lastName, String idNumber);

	//Find archive doctor accounts
	List<NurseEntity> findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
			boolean archived, String firstName, String lastName, String idNumber);

	List<NurseEntity> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
}
