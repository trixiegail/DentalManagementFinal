package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Integer>{
	DoctorEntity findByIdNumberAndPassword(String idNumber, String password);
	List<DoctorEntity> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname);
}
