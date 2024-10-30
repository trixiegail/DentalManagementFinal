package com.dentalmanagement.DentalManagement.Controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
import com.dentalmanagement.DentalManagement.Repository.StaffRepository;
import com.dentalmanagement.DentalManagement.Service.StaffService;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "https://projectyey.vercel.app/")
public class StaffController {
    @Autowired
    StaffService staffservice;

    @Autowired
    StaffRepository staffrepo;

    // Endpoint for doctor authentication
    @PostMapping("/login")
    public ResponseEntity<StaffEntity> authenticateStaff(@RequestBody StaffEntity loginRequest) {
        StaffEntity staff = staffservice.authenticateStaff(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Create a staff record
    @PostMapping("/insertStaff")
    public StaffEntity insertStaff(@RequestBody StaffEntity staff) {
        return staffservice.insertStaff(staff);
    }

    // Read all staff records in tblstaff
    @GetMapping("/getStaffs")
    public List<StaffEntity> getAllStaffs(){
        return staffservice.getAllStaffs();
    }

    // Update a staff record
    @PutMapping("/updateStaff/{id}")
    public StaffEntity updateStaff(@PathVariable int id, @RequestBody StaffEntity newStaffDetails) {
        return staffservice.updateStaff(id, newStaffDetails);
    }

    // Archive a staff record
    @PostMapping("/archiveStaff/{id}")
    public ResponseEntity<?> archiveStaff(@PathVariable int id) {
        try {
            StaffEntity archivedStaff = staffservice.archiveStaff(id);
            return ResponseEntity.ok(archivedStaff);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while archiving the student");
        }
    }

    //unarchive a staff
    @PostMapping("/unarchiveStaff/{id}")
    public ResponseEntity<?> unarchiveStaff(@PathVariable int id) {
        try {
            StaffEntity unarchiveStaff = staffservice.unarchiveStaff(id);
            return ResponseEntity.ok(unarchiveStaff);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the staff");
        }
    }

    //get all archived staff accounts
    @GetMapping("/getAllArchivedStaffs")
    public ResponseEntity<List<StaffEntity>> getArchivedStaffs() {
        List<StaffEntity> archivedStaffs = staffservice.getAllArchiveStaffs();
        return ResponseEntity.ok(archivedStaffs);
    }

    //search for archive accounts of staff
    @GetMapping("search/archivedStaffs")
    public ResponseEntity<List<StaffEntity>> searchArchivedStaffs(@RequestParam String keyword) {
        List<StaffEntity> archivedStaffs = staffrepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
                true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedStaffs);
    }

    // Search for doctors by keyword
    @GetMapping("/searchStaffs")
    public ResponseEntity<List<StaffEntity>> searchStaffs(@RequestParam String keyword) {
        List<StaffEntity> staffs = staffservice.searchStaff(keyword);
        return ResponseEntity.ok(staffs);
    }

    @PostMapping("/uploadProfilePicture/{staffId}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable int staffId,@RequestParam("image") MultipartFile file){
        return new ResponseEntity<>(staffservice.uploadImage(staffId, file), HttpStatus.OK);
    }

    @GetMapping("/getProfilePicture/{staffId}")
    public ResponseEntity<?> getProfilePicture(@PathVariable int staffId) throws IOException {

        String format = staffservice.getPictureFormat(staffId);

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
        byte[] profilePicture = staffservice.downloadStaffImage(staffId);
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).body(profilePicture);

    }
}
