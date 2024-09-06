package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.dentalmanagement.DentalManagement.Repository.StudentRepository;
import com.dentalmanagement.DentalManagement.Service.StudentService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class StudentController {
    @Autowired
    StudentService studservice;
    StudentRepository studrepo;
    
    // Endpoint for student authentication
    @PostMapping("/login")
    public ResponseEntity<StudentEntity> login(@RequestBody StudentEntity loginRequest) {
        StudentEntity student = studservice.authenticate(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    // Create a student record
    @PostMapping("/insertStudent")
    public StudentEntity insertStudent(@RequestBody StudentEntity student) {
        return studservice.insertStudent(student);
    }
    
    // Read all student records in tblstudent
    @GetMapping("/students")
    public List<StudentEntity> getAllStudents(){
        return studservice.getAllStudents();
    }
    
    // Update a student record
    @PutMapping("/updateStudent/{id}")
    public StudentEntity updateStudent(@PathVariable int id, @RequestBody StudentEntity newStudentDetails) {
        return studservice.updateStudent(id, newStudentDetails);
    }

    // Archive a student record
    @PostMapping("/archive/{id}")
    public ResponseEntity<?> archiveUser(@PathVariable int id) {
        try {
            StudentEntity archivedStudent = studservice.archiveUser(id);
            return ResponseEntity.ok(archivedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while archiving the student");
        }
    }
    
    //unarchive a student
    @PostMapping("/students/unarchive/{id}")
    public ResponseEntity<?> unarchiveUser(@PathVariable int id) {
        try {
            StudentEntity unarchivedStudent = studservice.unarchiveUser(id);
            return ResponseEntity.ok(unarchivedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the student");
        }
    }
    
    //get all archived accounts
    @GetMapping("/students/archived")
    public ResponseEntity<List<StudentEntity>> getArchivedStudents() {
        List<StudentEntity> archivedStudents = studservice.getAllArchiveStudents();
        return ResponseEntity.ok(archivedStudents);
    }
    
    //search for archive accounts of students
    @GetMapping("/students/search/archived")
    public ResponseEntity<List<StudentEntity>> searchArchivedStudents(@RequestParam String keyword) {
        List<StudentEntity> archivedStudents = studrepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
        		true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedStudents);
    }

    // Search for students by keyword
    @GetMapping("/students/search")
    public ResponseEntity<List<StudentEntity>> searchStudents(@RequestParam String keyword) {
        List<StudentEntity> students = studservice.searchStudents(keyword);
        return ResponseEntity.ok(students);
    }

    // Search for students by department
	@GetMapping("/students/searchByDepartmentAndYear")
	public ResponseEntity<List<StudentEntity>> searchStudentsByDepartmentAndYear(
		@RequestParam String department,
		@RequestParam String yearLevel
	) {
		List<StudentEntity> students = studservice.searchStudentsByDepartmentAndYear(department, yearLevel);
		return ResponseEntity.ok(students);
	}
	@GetMapping("/students/searchByDepartment")
	public ResponseEntity<List<StudentEntity>> searchStudentsByDepartment(@RequestParam String department) {
    	List<StudentEntity> students = studservice.searchStudentsByDepartment(department);
    	return ResponseEntity.ok(students);
	}	
	
    // Search for students by year level
    @GetMapping("/students/searchByYearLevel")
    public ResponseEntity<List<StudentEntity>> searchStudentsByYearLevel(@RequestParam String yearLevel) {
        List<StudentEntity> students = studservice.searchStudentsByYearLevel(yearLevel);
        return ResponseEntity.ok(students);
    }
}
