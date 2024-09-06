package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dentalmanagement.DentalManagement.DTO.UserDTO;
import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Entity.NurseEntity;
import com.dentalmanagement.DentalManagement.Entity.OtherUserRole;
import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Repository.NurseRepository;
import com.dentalmanagement.DentalManagement.Repository.StaffRepository;
import com.dentalmanagement.DentalManagement.Service.DoctorService;
import com.dentalmanagement.DentalManagement.Service.NurseService;
import com.dentalmanagement.DentalManagement.Service.StaffService;
import com.dentalmanagement.DentalManagement.Service.StudentService;

//UserController.java
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    private final NurseService nurseService;
    private final DoctorService doctorService;
    private final StaffService staffService;
    
    @Autowired
    StaffRepository staffrepo;
    NurseRepository nurserepo;


    @Autowired
    public UserController(NurseService nurseService, DoctorService doctorService, StaffService staffService, 
                          StudentService studentService) {
        this.nurseService = nurseService;
        this.doctorService = doctorService;
        this.staffService = staffService;
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
    
    
 // Endpoint for nurse authentication
    @PostMapping("/login-nurse")
    public ResponseEntity<NurseEntity> login(@RequestBody NurseEntity loginRequest) {
        NurseEntity nurse = nurseService.authenticateNurse(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (nurse != null) {
            return ResponseEntity.ok(nurse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
 // Endpoint for staff authentication
    @PostMapping("/login-staff")
    public ResponseEntity<StaffEntity> login(@RequestBody StaffEntity loginRequest) {
        StaffEntity staff = staffService.authenticateStaff(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
 // Endpoint for doctor authentication
    @PostMapping("/login-doctor")
    public ResponseEntity<DoctorEntity> login(@RequestBody DoctorEntity loginRequest) {
        DoctorEntity doctor = doctorService.authenticateDoctor(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    // Get all doctors
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorEntity>> getAllDoctorAccounts() {
        List<DoctorEntity> doctorAccounts = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorAccounts);
    }

    // Get all nurses
    @GetMapping("/nurses")
    public ResponseEntity<List<NurseEntity>> getAllNurseAccounts() {
        List<NurseEntity> nurseAccounts = nurseService.getAllNurses();
        return ResponseEntity.ok(nurseAccounts);
    }
    
    // Get all staffs
    @GetMapping("/staffs")
    public ResponseEntity<List<StaffEntity>> getAllStaffAccounts(){
        List<StaffEntity> staffAccounts = staffService.getAllStaffs();
        return ResponseEntity.ok(staffAccounts);
    }
    
    // Search for staff
    @GetMapping("/staffs/search")
    public List<StaffEntity> searchStaff(@RequestParam String keyword) {
        return staffService.searchStaff(keyword);
    }
    
 // Search for nurse
    @GetMapping("/nurses/search")
    public List<NurseEntity> searchNurse(@RequestParam String keyword) {
        return nurseService.searchNurse(keyword);
    }
    
    // Search for doctor
    @GetMapping("/doctors/search")
    public List<DoctorEntity> searchDoctor(@RequestParam String keyword) {
        return doctorService.searchDoctor(keyword);
    }

    // Update a staff account
    @PutMapping("/updateStaff/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable int id, @RequestBody StaffEntity newStaffDetails) {
        try {
            StaffEntity updatedStaff = staffService.updateStaff(id, newStaffDetails);
            return ResponseEntity.ok(updatedStaff);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User with ID " + id + " does not exist");
        }
    }
    @PutMapping("/update/nurse/{id}")
    public ResponseEntity<?> updateNurse(@PathVariable int id, @RequestBody NurseEntity newNurseDetails) {
        try {
            NurseEntity updatedNurse = nurseService.updateNurse(id, newNurseDetails);
            return ResponseEntity.ok(updatedNurse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Nurse with ID " + id + " does not exist");
        }
    }
    
    //update doctor
    @PutMapping("/update/doctors/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable int id, @RequestBody DoctorEntity newDoctorDetails) {
        try {
            DoctorEntity updatedDoctor = doctorService.updateDoctor(id, newDoctorDetails);
            return ResponseEntity.ok(updatedDoctor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Doctor with ID " + id + " does not exist");
        }
    }
    
 // Archive a nurse record
    @PostMapping("/nurses/archiveNurse/{id}")
    public ResponseEntity<?> archiveNurse(@PathVariable int id) {
        try {
            NurseEntity archivedNurse = nurseService.archiveNurse(id);
            return ResponseEntity.ok(archivedNurse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while archiving the student");
        }
    }
    
    //unarchive a nurse
    @PostMapping("/unarchiveNurse/{id}")
    public ResponseEntity<?> unarchiveNurse(@PathVariable int id) {
        try {
            NurseEntity unarchivedNurse = nurseService.unarchiveNurse(id);
            return ResponseEntity.ok(unarchivedNurse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the student");
        }
    }
    
    //search for archive accounts of nurse
    @GetMapping("/nurses/search/archived")
    public ResponseEntity<List<NurseEntity>> searchArchivedNurse(@RequestParam String keyword) {
        List<NurseEntity> archivedNurses = nurserepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
        		true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedNurses);
    }
    
    //get all archived accounts of nurse
    @GetMapping("/nurses/archived")
    public ResponseEntity<List<NurseEntity>> getArchivedNurse() {
        List<NurseEntity> archivedNurse = nurseService.getAllArchiveNurses();
        return ResponseEntity.ok(archivedNurse);
    }
    
 // Archive a staff record
    @PostMapping("/staffs/archiveStaff/{id}")
    public ResponseEntity<?> archiveUser(@PathVariable int id) {
        try {
            StaffEntity archivedStaff = staffService.archiveUser(id);
            return ResponseEntity.ok(archivedStaff);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while archiving the student");
        }
    }
    
    //unarchive a staff
    @PostMapping("/unarchiveStaff/{id}")
    public ResponseEntity<?> unarchiveUser(@PathVariable int id) {
        try {
            StaffEntity unarchivedStaff = staffService.unarchiveUser(id);
            return ResponseEntity.ok(unarchivedStaff);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the student");
        }
    }
    
    //get all archived accounts
    @GetMapping("/staffs/archived")
    public ResponseEntity<List<StaffEntity>> getArchivedStaffs() {
        List<StaffEntity> archivedStaff = staffService.getAllArchiveStaffs();
        return ResponseEntity.ok(archivedStaff);
    }
    
    //search for archive accounts of staffs
    @GetMapping("/staffs/search/archived")
    public ResponseEntity<List<StaffEntity>> searchArchivedStaff(@RequestParam String keyword) {
        List<StaffEntity> archivedStaffs = staffrepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
        		true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedStaffs);
    }

}
