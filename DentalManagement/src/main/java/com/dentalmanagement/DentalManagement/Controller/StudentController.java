package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Service.StudentService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class StudentController {
	@Autowired
	StudentService studservice;
	
	// Endpoint for student authentication
    @PostMapping("/authenticate")
    public boolean authenticateStudent(@RequestBody StudentEntity student) {
        return studservice.authenticate(student.getIdNumber(), student.getPassword());
    }
    
	//C- Create a student record 
	@PostMapping("/insertStudent")
	public StudentEntity insertStudent(@RequestBody StudentEntity student) {
		return studservice.insertStudent(student);
	}
	
	//R- read all student records in tblstudent
	@GetMapping("/students")
	public List<StudentEntity> getAllStudents(){
		return studservice.getAllStudents();
	}
	
	//U- update a student record
	@PutMapping("/updateStudent")
	public StudentEntity updateStudent(@RequestParam int id, @RequestBody StudentEntity newStudentDetails) {
		return studservice.updateStudent(id, newStudentDetails);
	}
	
	@PostMapping("/archive/{id}")
    public ResponseEntity<StudentEntity> archiveUser(@PathVariable int id, @RequestBody StudentEntity student) throws IllegalAccessException {
        StudentEntity archivedUser = studservice.archiveUser(id, student);
        return ResponseEntity.ok(archivedUser);
    }
	
	@GetMapping("/student/search")
	 public List<StudentEntity> searchStudent(@RequestParam String keyword) {
	     return studservice.searchStudent(keyword);
	 }

	 @GetMapping("/students/search")
	    public ResponseEntity<List<StudentEntity>> searchStudents(@RequestParam String keyword) {
	        List<StudentEntity> students = studservice.searchStudents(keyword);
	        return ResponseEntity.ok(students);
	    }
}
