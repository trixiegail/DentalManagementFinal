package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.StaffEntity;



public interface StaffRepository extends JpaRepository<StaffEntity, Integer>{
	StaffEntity findByIdNumberAndPassword(String idNumber, String password);
	// Find staffs by archived status
	List<StaffEntity> findByArchived(boolean archived);

	// Search for staffs by first name, last name, or ID number
	List<StaffEntity> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(String firstName, String lastName, String idNumber);

	//Find archive staff accounts
	List<StaffEntity> findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
			boolean archived, String firstName, String lastName, String idNumber);

	List<StaffEntity> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
}
