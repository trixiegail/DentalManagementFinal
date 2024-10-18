package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Util.ImageUtils;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.DTO.UserDTO;
import com.dentalmanagement.DentalManagement.Entity.NurseEntity;
import com.dentalmanagement.DentalManagement.Entity.OtherUserRole;
import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
import com.dentalmanagement.DentalManagement.Repository.NurseRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class NurseService {
	@Autowired
	private NurseRepository nurserepo;
	
	//authentication for nurse
    public NurseEntity authenticateNurse(String idNumber, String password) {
        return nurserepo.findByIdNumberAndPassword(idNumber, password);
    }
    
//    //insert/create a nurse
//    public NurseEntity createUser(UserDTO request) {
//    	NurseEntity nurse = new NurseEntity();
//
//    	nurse.setIdNumber(request.getIdNumber());
//        nurse.setFirstname(request.getFirstname());
//        nurse.setLastname(request.getLastname());
//        nurse.setBirthdate(request.getBirthdate());
//        nurse.setGender(request.getGender());
//        nurse.setEmail(request.getEmail());
//        nurse.setPassword(request.getPassword());
//    	nurse.setRole(OtherUserRole.NURSE);
//    	return nurserepo.save(nurse);
//    }

    // Create or insert nurse record in tblnurse
    public NurseEntity insertNurse(NurseEntity nurse) {
        return nurserepo.save(nurse);
    }
    
    //get the nurses
    public List<NurseEntity> getAllNurses(){
    	return nurserepo.findAll();
    }
	public void deleteNurse(int id) {
        nurserepo.deleteById(id);
    }
    
    //update nurse
    public NurseEntity updateNurse(int id, NurseEntity newNurseDetails) {
    	NurseEntity nurse = nurserepo.findById(id)
    			.orElseThrow(() -> new NoSuchElementException("User " + id + " does not exist"));
    	
    	nurse.setIdNumber(newNurseDetails.getIdNumber());
    	nurse.setFirstname(newNurseDetails.getFirstname());
    	nurse.setLastname(newNurseDetails.getLastname());
    	nurse.setBirthdate(newNurseDetails.getBirthdate());
    	nurse.setGender(newNurseDetails.getGender());
    	nurse.setEmail(newNurseDetails.getEmail());
    	nurse.setPassword(newNurseDetails.getPassword());
    	
    	return nurserepo.save(nurse);
    }
    
    //archive nurse account
    public NurseEntity archiveNurse(int id) {
        NurseEntity nurseToArchive = nurserepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Nurse " + id + " does not exist"));
        
        nurseToArchive.setArchived(true); // Assuming you have an 'archived' field
        return nurserepo.save(nurseToArchive);
    }
    
    //unarchive a nurse
    public NurseEntity unarchiveNurse(int id) {
        NurseEntity nurseToUnarchive = nurserepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Nurse " + id + " does not exist"));
        
        nurseToUnarchive.setArchived(false);
        return nurserepo.save(nurseToUnarchive);
    }
    
    // get all archived accounts of nurse
    public List<NurseEntity> getAllArchiveNurses() {
        return nurserepo.findByArchived(true);
    }
    
  //search for nurse based on letters typed
    public List<NurseEntity> searchNurse(String keyword) {
        // Search for staff members whose first name or last name contains the keyword
        return nurserepo.findByFirstnameContainingOrLastnameContaining(keyword, keyword);
    }

    // Search nurses by first name, last name, or ID number
    public List<NurseEntity> searchNurses(String keyword) {
        // Call the repository method to perform the search
        return nurserepo.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(keyword, keyword, keyword);
    }

    @Transactional
    public String uploadImage(int id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        try {
            NurseEntity nurse = nurserepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Nurse " + id + " does not exist"));

            byte[] compressedImage = ImageUtils.compressImage(file.getBytes());
            nurse.setProfilePictureName(file.getOriginalFilename());
            nurse.setNurseProfile(compressedImage);
            nurserepo.save(nurse);
            return "Uploaded user image successfully";

        } catch (IOException e) {
            throw new RuntimeException("Failed to read the image data", e);
        }
    }




    public String getPictureFormat(int id){
        NurseEntity nurse = nurserepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Nurse " + id + " does not exist"));
        String pictureName = nurse.getProfilePictureName();
        return pictureName.substring(pictureName.lastIndexOf(".") + 1);
    }


    @Transactional(readOnly = true)
    public byte[] downloadNurseImage(int id){
        NurseEntity nurse = nurserepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));
        return ImageUtils.decompressImage(nurse.getNurseProfile());
    }


}
