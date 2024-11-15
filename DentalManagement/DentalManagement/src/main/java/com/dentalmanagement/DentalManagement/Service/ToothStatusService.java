package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.DTO.ToothStatusDTO;
import com.dentalmanagement.DentalManagement.Entity.StudentCheckup;
import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Entity.ToothStatus;
import com.dentalmanagement.DentalManagement.Repository.StudentRepository;
import com.dentalmanagement.DentalManagement.Repository.ToothStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToothStatusService {

    @Autowired
    private ToothStatusRepository toothStatusRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void saveToothStatuses(String studentIdNumber, List<ToothStatus> toothStatuses) {
        StudentEntity student = studentRepository.findByIdNumber(studentIdNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentIdNumber));

        // Set the student entity and student number on each ToothStatus
        toothStatuses.forEach(toothStatus -> {
            toothStatus.setStudent(student);
            toothStatus.setStudentNumber(student.getIdNumber());
            toothStatus.setSavedAt(new Date());
        });

        toothStatusRepository.saveAll(toothStatuses);
    }


    public List<ToothStatusDTO> getStudentToothStatuses(String studentIdNumber) {
        List<ToothStatus> toothStatuses = toothStatusRepository.findByStudent_IdNumber(studentIdNumber);

        // Ensure proper mapping with type inference
        return toothStatuses.stream()
                .map(toothStatus -> new ToothStatusDTO(
                        toothStatus.getToothNumber(),
                        toothStatus.getStatus(),
                        toothStatus.getSavedAt()
                ))
                .collect(Collectors.toList());
    }


}