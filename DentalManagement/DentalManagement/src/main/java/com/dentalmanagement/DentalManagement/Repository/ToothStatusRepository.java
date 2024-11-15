package com.dentalmanagement.DentalManagement.Repository;

import com.dentalmanagement.DentalManagement.Entity.StudentCheckup;
import com.dentalmanagement.DentalManagement.Entity.ToothStatus;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToothStatusRepository extends JpaRepository<ToothStatus, Integer> {
    List<ToothStatus> findByStudent_IdNumber(String studentIdNumber);
}