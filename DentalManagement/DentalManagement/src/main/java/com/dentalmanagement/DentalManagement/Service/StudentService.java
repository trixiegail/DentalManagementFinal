package com.dentalmanagement.DentalManagement.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.dentalmanagement.DentalManagement.Util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService{

    @Autowired
    private StudentRepository studentRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    // Authenticate a student by idNumber and studentPassword
    public StudentEntity authenticate(String idNumber, String plainPassword) {
        StudentEntity student = studentRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new NoSuchElementException("Invalid credentials"));

        if (encoder.matches(plainPassword, student.getPassword())) {
            return student;
        } else {
            throw new NoSuchElementException("Invalid credentials");
        }
    }

    // Create or insert student record in tblstudent
    public StudentEntity insertStudent(StudentEntity student) {
        student.setPassword(encoder.encode(student.getPassword())); // Hash password
        return studentRepository.save(student);
    }

    // Read all records in tblstudent
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    // Update a student
    public StudentEntity updateStudent(int id, StudentEntity newStudentDetails) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));

        // Update other fields
        student.setIdNumber(newStudentDetails.getIdNumber());
        student.setFirstname(newStudentDetails.getFirstname());
        student.setLastname(newStudentDetails.getLastname());
        student.setDepartment(newStudentDetails.getDepartment());
        student.setProgram(newStudentDetails.getProgram());
        student.setYearLevel(newStudentDetails.getYearLevel());
        student.setBirthdate(newStudentDetails.getBirthdate());
        student.setEmail(newStudentDetails.getEmail());
        student.setGender(newStudentDetails.getGender());

        // Only re-hash and set the password if it's changed
        if (!newStudentDetails.getPassword().equals(student.getPassword())) {
            student.setPassword(encoder.encode(newStudentDetails.getPassword()));
        }

        return studentRepository.save(student);
    }


    //archive a student
    public StudentEntity archiveUser(int id) {
        StudentEntity studentToArchive = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));

        studentToArchive.setArchived(true); // Assuming you have an 'archived' field
        return studentRepository.save(studentToArchive);
    }

    //unarchive a student
    public StudentEntity unarchiveUser(int id) {
        StudentEntity studentToUnarchive = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));

        studentToUnarchive.setArchived(false);
        return studentRepository.save(studentToUnarchive);
    }

    // get all archived accounts
    public List<StudentEntity> getAllArchiveStudents() {
        return studentRepository.findByArchived(true);
    }

    //get all non-archived accounts
    public List<StudentEntity> getNonArchivedStudents() {
        return studentRepository.findByArchived(false);
    }

    // Search students by first name, last name, or ID number
    public List<StudentEntity> searchStudents(String keyword) {
        // Call the repository method to perform the search
        return studentRepository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(keyword, keyword, keyword);
    }

    // Search students by department
    public List<StudentEntity> searchStudentsByDepartment(String department) {
        // Call the repository method to perform the search by department
        return studentRepository.findByDepartment(department);
    }

    // Search students by year level
    public List<StudentEntity> searchStudentsByYearLevel(String yearLevel) {
        // Call the repository method to perform the search by year level
        return studentRepository.findByYearLevel(yearLevel);
    }
    public List<StudentEntity> searchStudentsByDepartmentAndYear(String department, String yearLevel) {
        return studentRepository.findByDepartmentAndYearLevel(department, yearLevel);
    }

    @Transactional
    public String uploadImage(int id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        try {
            StudentEntity student = studentRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));

            byte[] compressedImage = ImageUtils.compressImage(file.getBytes());
            student.setProfilePictureName(file.getOriginalFilename());
            student.setStudentProfile(compressedImage);
            studentRepository.save(student);
            return "Uploaded user image successfully";

        } catch (IOException e) {
            throw new RuntimeException("Failed to read the image data", e);
        }
    }

    public StudentEntity getIdNumber(String idNumber) {
        return studentRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + idNumber));
    }




    public String getPictureFormat(int id){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));
        String pictureName = student.getProfilePictureName();
        return pictureName.substring(pictureName.lastIndexOf(".") + 1);
    }


    @Transactional(readOnly = true)
    public byte[] downloadStudentImage(int id){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));
        return ImageUtils.decompressImage(student.getStudentProfile());
    }
}