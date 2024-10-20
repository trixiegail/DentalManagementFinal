package com.dentalmanagement.DentalManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.StudentCheckup;
import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Repository.StudentCheckupRepository;
import com.dentalmanagement.DentalManagement.Repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCheckupService {

    @Autowired
    private StudentCheckupRepository studentCheckupRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentCheckup> getAllCheckups() {
        return studentCheckupRepository.findAll();
    }

    public Optional<StudentCheckup> getCheckupById(Long id) {
        return studentCheckupRepository.findById(id);
    }

    public StudentCheckup createOrUpdateCheckup(StudentCheckup checkup) {
        return studentCheckupRepository.save(checkup);
    }

    public void deleteCheckup(Long id) {
        studentCheckupRepository.deleteById(id);
    }
    
    public StudentCheckup saveCheckup(String idNumber, StudentCheckup checkup) {
        // Find the student by their ID Number
        Optional<StudentEntity> studentOptional = studentRepository.findByIdNumber(idNumber);

        if (studentOptional.isPresent()) {
            StudentEntity student = studentOptional.get();

            // Associate the checkup with the student
            checkup.setStudent(student); // This is the crucial step to avoid null 'student_id'
            checkup.setStudentIdNumber(idNumber);
            // Save the checkup
            return studentCheckupRepository.save(checkup);
        } else {
            throw new RuntimeException("Student not found with ID Number: " + idNumber);
        }
    }
    
    public List<StudentCheckup> getCheckupsByStudentIdNumber(String idNumber) {
        return studentCheckupRepository.findByStudentIdNumber(idNumber);
    }


}
