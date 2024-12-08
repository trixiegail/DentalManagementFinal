package com.dentalmanagement.DentalManagement.Controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
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

import com.dentalmanagement.DentalManagement.Entity.NurseEntity;
import com.dentalmanagement.DentalManagement.Repository.NurseRepository;
import com.dentalmanagement.DentalManagement.Service.NurseService;

@RestController
@RequestMapping("/nurse")
@CrossOrigin(origins = "https://projectyey.vercel.app/")
public class NurseController {
    @Autowired
    NurseService nurseservice;

    @Autowired
    NurseRepository nurserepo;

    // Endpoint for nurse authentication
    @PostMapping("/login")
    public ResponseEntity<NurseEntity> authenticateNurse(@RequestBody NurseEntity loginRequest) {
        NurseEntity nurse = nurseservice.authenticateNurse(loginRequest.getIdNumber(), loginRequest.getPassword());
        if (nurse != null) {
            return ResponseEntity.ok(nurse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Create a nurse record
    @PostMapping("/insertNurse")
    public NurseEntity insertNurse(@RequestBody NurseEntity nurse) {
        return nurseservice.insertNurse(nurse);
    }

    // Read all nurse records in tblnurse
    @GetMapping("/getNurses")
    public List<NurseEntity> getAllNurses(){
        return nurseservice.getAllNurses();
    }

    // Update a nurse record
    @PutMapping("/updateNurse/{id}")
    public NurseEntity updateNurse(@PathVariable int id, @RequestBody NurseEntity newNurseDetails) {
        return nurseservice.updateNurse(id, newNurseDetails);
    }

    // Archive a nurse record
    @PostMapping("/archiveNurse/{id}")
    public ResponseEntity<?> archiveNurse(@PathVariable int id) {
        try {
            NurseEntity archivedNurse = nurseservice.archiveNurse(id);
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
            NurseEntity unarchivedNurse = nurseservice.unarchiveNurse(id);
            return ResponseEntity.ok(unarchivedNurse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unarchiving the student");
        }
    }

    //get all archived nurse accounts
    @GetMapping("/getAllArchivedNurses")
    public ResponseEntity<List<NurseEntity>> getArchivedNurses() {
        List<NurseEntity> archivedNurses = nurseservice.getAllArchiveNurses();
        return ResponseEntity.ok(archivedNurses);
    }

    //search for archive accounts of nurse
    @GetMapping("/search/archivedNurses")
    public ResponseEntity<List<NurseEntity>> searchArchivedNurses(@RequestParam String keyword) {
        List<NurseEntity> archivedNurses = nurserepo.findByArchivedAndFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(
                true, keyword, keyword, keyword);
        return ResponseEntity.ok(archivedNurses);
    }

    // Search for nurses by keyword
    @GetMapping("/searchNurses")
    public ResponseEntity<List<NurseEntity>> searchNurses(@RequestParam String keyword) {
        List<NurseEntity> nurse = nurseservice.searchNurse(keyword);
        return ResponseEntity.ok(nurse);
    }

    @PostMapping("/uploadProfilePicture/{nurseId}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable int nurseId,@RequestParam("image") MultipartFile file){
        return new ResponseEntity<>(nurseservice.uploadImage(nurseId, file), HttpStatus.OK);
    }

    @GetMapping("/getProfilePicture/{nurseId}")
    public ResponseEntity<?> getProfilePicture(@PathVariable int nurseId) throws IOException {

        String format = nurseservice.getPictureFormat(nurseId);

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
        byte[] profilePicture = nurseservice.downloadNurseImage(nurseId);
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).body(profilePicture);

    }
}
