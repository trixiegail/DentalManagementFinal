package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer>{
	StudentEntity findByIdNumberAndPassword(String idNumber, String password);
	List<StudentEntity> findByArchived(boolean archived);
}
