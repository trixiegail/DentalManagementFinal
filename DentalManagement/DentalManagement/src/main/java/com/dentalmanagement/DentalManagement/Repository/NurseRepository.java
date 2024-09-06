package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.NurseEntity;


public interface NurseRepository extends JpaRepository<NurseEntity, Integer>{
	NurseEntity findByIdNumberAndPassword(String idNumber, String password);
	
	// Find nurses by archived status
    List<NurseEntity> findByArchived(boolean archived);
    
	List<NurseEntity> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
	
	List<NurseEntity> findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
		    boolean archived, String firstName, String lastName, String idNumber);
}
