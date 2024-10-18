package com.dentalmanagement.DentalManagement.Controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Repository.DoctorRepository;
import com.dentalmanagement.DentalManagement.Service.DoctorService;
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
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:5173/")
public class DoctorController {
    @Autowired
    DoctorService docservice;
    DoctorRepository docrepo;

    // Endpoint for doctor authentication
    @PostMapping("/login")
    public ResponseEntity<DoctorEntity> authenticateDoctor(@RequestBody DoctorEntity loginRequest) {
        DoctorEntity doctor = docservice.authenticateDoctor(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Create a doctor record
    @PostMapping("/insertDoctor")
    public DoctorEntity insertDoctor(@RequestBody DoctorEntity doctor) {
        return docservice.insertDoctor(doctor);
    }

    // Read all doctor records in tbldoctor
    @GetMapping("/getDoctors")
    public List<DoctorEntity> getAllDoctors(){
        return docservice.getAllDoctors();
    }

    // Update a doctor record
    @PutMapping("/updateDoctor/{id}")
    public DoctorEntity updateDoctor(@PathVariable int id, @RequestBody DoctorEntity newDoctorDetails) {
        return docservice.updateDoctor(id, newDoctorDetails);
    }

    // Archive a doctor record
    @PostMapping("/archiveDoctor/{id}")
    public ResponseEntity<?> archiveDoctor(@PathVariable int id) {
        try {
            DoctorEntity archivedDoctor = docservice.archiveDoctor(id);
            return ResponseEntity.ok(archivedDoctor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while archiving the student");
        }
    }

    //unarchive a doctor
    @PostMapping("/unarchiveDoctor/{id}")
    public ResponseEntity<?> unarchiveDoctor(@PathVariable int id) {
        try {
            DoctorEntity unarchivedDoctor = docservice.unarchiveDoctor(id);
            return ResponseEntity.ok(unarchivedDoctor);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the student");
        }
    }

    //get all archived doctor accounts
    @GetMapping("/getAllArchivedDoctors")
    public ResponseEntity<List<DoctorEntity>> getArchivedDoctors() {
        List<DoctorEntity> archivedDoctors = docservice.getAllArchiveDoctors();
        return ResponseEntity.ok(archivedDoctors);
    }

    //search for archive accounts of doctor
    @GetMapping("search/archivedDoctors")
    public ResponseEntity<List<DoctorEntity>> searchArchivedDoctors(@RequestParam String keyword) {
        List<DoctorEntity> archivedDoctors = docrepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
                true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedDoctors);
    }

    // Search for doctors by keyword
    @GetMapping("/searchDoctor")
    public ResponseEntity<List<DoctorEntity>> searchDoctors(@RequestParam String keyword) {
        List<DoctorEntity> doctors = docservice.searchDoctor(keyword);
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/uploadProfilePicture/{doctorId}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable int doctorId,@RequestParam("image") MultipartFile file){
        return new ResponseEntity<>(docservice.uploadImage(doctorId, file), HttpStatus.OK);
    }

    @GetMapping("/getProfilePicture/{doctorId}")
    public ResponseEntity<?> getProfilePicture(@PathVariable int doctorId) throws IOException {

        String format = docservice.getPictureFormat(doctorId);

        MediaType mediaType;

        switch (format) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "jpg":
            case "jpeg":
            case "jfif":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            case "bmp":
                mediaType = MediaType.valueOf("image/bmp");
                break;
            case "tiff":
                mediaType = MediaType.valueOf("image/tiff");
                break;
            case "webp":
                mediaType = MediaType.valueOf("image/webp");
                break;
            case "svg":
                mediaType = MediaType.valueOf("image/svg+xml");
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported Format: " + format);
        }
        byte[] profilePicture = docservice.downloadDoctorImage(doctorId);
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).body(profilePicture);

    }

}
