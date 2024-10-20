package com.dentalmanagement.DentalManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dentalmanagement.DentalManagement.Entity.StudentCheckup;

@Repository
public interface StudentCheckupRepository extends JpaRepository<StudentCheckup, Long> {
	List<StudentCheckup> findByStudentIdNumber(String studentIdNumber);
}

