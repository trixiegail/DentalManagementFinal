package com.dentalmanagement.DentalManagement.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Repository.StudentRepository;

@Service
@CrossOrigin(origins = "http://localhost:3000")
public class StudentService {
	@Autowired
	StudentRepository studrepo;
	
	// Authenticate a student by idNumber and studentPassword
    public boolean authenticate(String idNumber, String password) {
        // Find a student by idNumber and studentPassword
        StudentEntity student = studrepo.findByIdNumberAndPassword(idNumber, password);
        return student != null;
    }
	
	//C- Create or insert student record in tblstudent
	public StudentEntity insertStudent(StudentEntity student) {
		return studrepo.save(student);
	}
	
	//R- Read all records in tblstudent
	public List<StudentEntity> getAllStudents(){
		return studrepo.findAll();
	}
	
	//U- update a student
	@SuppressWarnings("finally")
	public StudentEntity updateStudent(int id, StudentEntity newStudentDetails) {
		StudentEntity student = new StudentEntity();
		
		try {
			//1. search the id number of the student that will be updated
			student = studrepo.findById(id).get();
			
			//2. update the record
			student.setIdNumber(newStudentDetails.getIdNumber());
			student.setFirstname(newStudentDetails.getFirstname());
			student.setLastname(newStudentDetails.getLastname());
			student.setDepartment(newStudentDetails.getDepartment());
			student.setProgram(newStudentDetails.getProgram());
			student.setYearLevel(newStudentDetails.getYearLevel());
			student.setBirthdate(newStudentDetails.getBirthdate());
			student.setEmail(newStudentDetails.getEmail());
			student.setPassword(newStudentDetails.getPassword());
		}catch(NoSuchElementException ex){
			throw new NoSuchElementException("Student "+id+" does not exist");
		}finally {
			return studrepo.save(student);
		}
	}
	
	public StudentEntity archiveUser(int id, StudentEntity student) throws IllegalAccessException{
	    StudentEntity studentToArchive = studrepo.findById(id)
	    		.orElseThrow(() -> new NoSuchElementException("User " + id + " does not exist"));
	    try {
	        if (studentToArchive != null) {
	        	return studrepo.save(studentToArchive);
	        }else {
	            // Handle the case where the user is not found (optional)
	        	throw new IllegalAccessException("Student not found.");
	        }
	    } catch (NoSuchElementException ex) {
	        // throw the exception if the student does not exist
	        throw new NoSuchElementException("Student " + id + " does not exist");
	    }
	}

	public List<StudentEntity> searchStudent(String keyword) {
		return studrepo.findByIdNumberContainingOrFirstnameContainingOrLastnameContaining(keyword, keyword, keyword);
	}
	
	// Search students by first name, last name, or ID number
    public List<StudentEntity> searchStudents(String keyword) {
        // Call the repository method to perform the search
        return studrepo.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(keyword, keyword, keyword);
    }
}
