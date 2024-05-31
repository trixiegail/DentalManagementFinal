package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dentalmanagement.DentalManagement.DTO.UserDTO;
import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Entity.NurseEntity;
import com.dentalmanagement.DentalManagement.Entity.OtherUserRole;
import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
import com.dentalmanagement.DentalManagement.Service.DoctorService;
import com.dentalmanagement.DentalManagement.Service.NurseService;
import com.dentalmanagement.DentalManagement.Service.StaffService;

//UserController.java
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

// private final AdminService adminService;
 private final NurseService nurseService;
 private final StaffService staffService;
 private final DoctorService doctorService;

 @Autowired
 public UserController(NurseService nurseService, DoctorService doctorService, StaffService staffService) {
//     this.adminService = adminService;
     this.nurseService = nurseService;
     this.staffService = staffService;
     this.doctorService = doctorService;
 }

 @PostMapping("/create")
 public ResponseEntity<?> createUser(@RequestBody UserDTO request) {
     // Determine user type based on request data
     OtherUserRole userType = request.getRole();

     // Create user based on user type
     switch (userType) {
         case NURSE:
             nurseService.createUser(request);
             break;
         
         case DOCTOR:
        	 doctorService.createUser(request);
        	 break;
        	 
         case STAFF:
        	 staffService.createUser(request);
        	 break;
        	 
         default:
             // Handle invalid user type
             return ResponseEntity.badRequest().body("Invalid user type");
     }

     return ResponseEntity.ok("User created successfully");
 }
 
 //get all doctors
 @GetMapping("/doctors")
 public ResponseEntity<List<DoctorEntity>> getAllDoctorAccounts() {
     List<DoctorEntity> doctorAccounts = doctorService.getAllDoctors();
     return ResponseEntity.ok(doctorAccounts);
 }

 //get all nurses
 @GetMapping("/nurses")
 public ResponseEntity<List<NurseEntity>> getAllNurseAccounts() {
     List<NurseEntity> nurseAccounts = nurseService.getAllNurses();
     return ResponseEntity.ok(nurseAccounts);
 }
 
 //get all staffs
 @GetMapping("/staffs")
 public ResponseEntity<List<StaffEntity>> getAllStaffAccounts(){
	 List<StaffEntity> staffAccounts = staffService.getAllStaffs();
	 return ResponseEntity.ok(staffAccounts);
 }
 
 @GetMapping("/staffs/search")
 public List<StaffEntity> searchStaff(@RequestParam String keyword) {
     return staffService.searchStaff(keyword);
 }
 
 @GetMapping("/nurses/search")
 public List<NurseEntity> searchNurse(@RequestParam String keyword) {
     return nurseService.searchNurse(keyword);
 }
 
 @GetMapping("/doctors/search")
 public List<DoctorEntity> searchDoctor(@RequestParam String keyword) {
     return doctorService.searchDoctor(keyword);
 }
}

