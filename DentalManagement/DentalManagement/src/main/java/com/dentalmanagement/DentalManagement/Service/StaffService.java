package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Util.ImageUtils;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.DTO.UserDTO;
import com.dentalmanagement.DentalManagement.Entity.OtherUserRole;
import com.dentalmanagement.DentalManagement.Entity.StaffEntity;
import com.dentalmanagement.DentalManagement.Repository.StaffRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StaffService {
	@Autowired
	private StaffRepository staffrepo;
	
	//authentication for staff
    public StaffEntity authenticateStaff(String idNumber, String password) {
        return staffrepo.findByIdNumberAndPassword(idNumber, password);
    }
    
    //insert/create a staff
//    public StaffEntity createUser(UserDTO request) {
//    	StaffEntity staff = new StaffEntity();
//
//    	staff.setIdNumber(request.getIdNumber());
//        staff.setFirstname(request.getFirstname());
//        staff.setLastname(request.getLastname());
//        staff.setBirthdate(request.getBirthdate());
//        staff.setGender(request.getGender());
//        staff.setEmail(request.getEmail());
//        staff.setPassword(request.getPassword());
//    	staff.setRole(OtherUserRole.STAFF);
//    	return staffrepo.save(staff);
//    }

    // Create or insert doctor record in tbldoctor
    public StaffEntity insertStaff(StaffEntity staff) {
        return staffrepo.save(staff);
    }
    
    //get the staffs
    public List<StaffEntity> getAllStaffs(){
    	return staffrepo.findAll();
    }
    
    //update staff
    public StaffEntity updateStaff(int id, StaffEntity newStaffDetails) {
    	StaffEntity staff = staffrepo.findById(id)
    			.orElseThrow(() -> new NoSuchElementException("User " + id + " does not exist"));
    	
    	staff.setIdNumber(newStaffDetails.getIdNumber());
    	staff.setFirstname(newStaffDetails.getFirstname());
    	staff.setLastname(newStaffDetails.getLastname());
    	staff.setBirthdate(newStaffDetails.getBirthdate());
    	staff.setGender(newStaffDetails.getGender());
    	staff.setEmail(newStaffDetails.getEmail());
    	staff.setPassword(newStaffDetails.getPassword());
    	
    	return staffrepo.save(staff);
    }
    
  //archive a staff
    public StaffEntity archiveStaff(int id) {
        StaffEntity staffToArchive = staffrepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Staff " + id + " does not exist"));
        
        staffToArchive.setArchived(true); // Assuming you have an 'archived' field
        return staffrepo.save(staffToArchive);
    }
    
    //unarchive a staff
    public StaffEntity unarchiveStaff(int id) {
        StaffEntity staffToUnarchive = staffrepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Staff " + id + " does not exist"));
        
        staffToUnarchive.setArchived(false);
        return staffrepo.save(staffToUnarchive);
    }
    
    // get all archived accounts
    public List<StaffEntity> getAllArchiveStaffs() {
        return staffrepo.findByArchived(true);
    }
    
  //search for staff based on letters typed
    public List<StaffEntity> searchStaff(String keyword) {
        // Search for staff members whose first name or last name contains the keyword
        return staffrepo.findByFirstnameContainingOrLastnameContaining(keyword, keyword);
    }

    @Transactional
    public String uploadImage(int id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        try {
            StaffEntity staff = staffrepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Staff " + id + " does not exist"));

            byte[] compressedImage = ImageUtils.compressImage(file.getBytes());
            staff.setProfilePictureName(file.getOriginalFilename());
            staff.setStaffProfile(compressedImage);
            staffrepo.save(staff);
            return "Uploaded user image successfully";

        } catch (IOException e) {
            throw new RuntimeException("Failed to read the image data", e);
        }
    }




    public String getPictureFormat(int id){
        StaffEntity staff = staffrepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Staff " + id + " does not exist"));
        String pictureName = staff.getProfilePictureName();
        return pictureName.substring(pictureName.lastIndexOf(".") + 1);
    }


    @Transactional(readOnly = true)
    public byte[] downloadStaffImage(int id){
        StaffEntity staff = staffrepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Staff" + id + " does not exist"));
        return ImageUtils.decompressImage(staff.getStaffProfile());
    }

}
