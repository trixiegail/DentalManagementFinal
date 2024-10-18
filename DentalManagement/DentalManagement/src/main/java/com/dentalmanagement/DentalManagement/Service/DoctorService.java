package com.dentalmanagement.DentalManagement.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.dentalmanagement.DentalManagement.Entity.StudentEntity;
import com.dentalmanagement.DentalManagement.Util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.DoctorEntity;
import com.dentalmanagement.DentalManagement.Repository.DoctorRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository docrepo;
	
	public DoctorEntity authenticateDoctor(String idNumber, String password) {
		return docrepo.findByIdNumberAndPassword(idNumber, password);
	}
	
//	public DoctorEntity createUser(UserDTO request) {
//		DoctorEntity doctor = new DoctorEntity();
//
//		doctor.setIdNumber(request.getIdNumber());
//        doctor.setFirstname(request.getFirstname());
//        doctor.setLastname(request.getLastname());
//        doctor.setBirthdate(request.getBirthdate());
//        doctor.setGender(request.getGender());
//        doctor.setEmail(request.getEmail());
//        doctor.setPassword(request.getPassword());
//    	doctor.setRole(OtherUserRole.DOCTOR);
//
//		return docrepo.save(doctor);
//	}

	// Create or insert doctor record in tbldoctor
	public DoctorEntity insertDoctor(DoctorEntity doctor) {
		return docrepo.save(doctor);
	}
	
	//read all doctors
    public List<DoctorEntity> getAllDoctors() {
        return docrepo.findAll();
    }
    
    //update doctors
    public DoctorEntity updateDoctor(int id, DoctorEntity newDoctorDetails) {
    	DoctorEntity doctor = docrepo.findById(id)
    			.orElseThrow(() -> new NoSuchElementException("User " + id + " does not exist"));
    	
    	doctor.setIdNumber(newDoctorDetails.getIdNumber());
    	doctor.setFirstname(newDoctorDetails.getFirstname());
    	doctor.setLastname(newDoctorDetails.getLastname());
    	doctor.setBirthdate(newDoctorDetails.getBirthdate());
    	doctor.setGender(newDoctorDetails.getGender());
    	doctor.setEmail(newDoctorDetails.getEmail());
    	doctor.setPassword(newDoctorDetails.getPassword());
		doctor.setDoctorProfile(newDoctorDetails.getDoctorProfile());
		doctor.setProfilePictureName(newDoctorDetails.getProfilePictureName());

    	return docrepo.save(doctor);
    }


	//archive a doctor
	public DoctorEntity archiveDoctor(int id) {
		DoctorEntity doctorToArchive = docrepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Doctor " + id + " does not exist"));

		doctorToArchive.setArchived(true); // Assuming you have an 'archived' field
		return docrepo.save(doctorToArchive);
	}

	//unarchive a doctor
	public DoctorEntity unarchiveDoctor(int id) {
		DoctorEntity doctorToUnarchive = docrepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Doctor " + id + " does not exist"));

		doctorToUnarchive.setArchived(false);
		return docrepo.save(doctorToUnarchive);
	}

	// get all archived accounts
	public List<DoctorEntity> getAllArchiveDoctors() {
		return docrepo.findByArchived(true);
	}

	// Search doctors by first name, last name, or ID number
	public List<DoctorEntity> searchDoctors(String keyword) {
		// Call the repository method to perform the search
		return docrepo.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrIdNumberContaining(keyword, keyword, keyword);
	}

  //search for staff based on letters typed
    public List<DoctorEntity> searchDoctor(String keyword) {
        // Search for staff members whose first name or last name contains the keyword
        return docrepo.findByFirstnameContainingOrLastnameContaining(keyword, keyword);
    }

	@Transactional
	public String uploadImage(int id, MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}
		try {
			DoctorEntity doctor = docrepo.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Student " + id + " does not exist"));

			byte[] compressedImage = ImageUtils.compressImage(file.getBytes());
			doctor.setProfilePictureName(file.getOriginalFilename());
			doctor.setDoctorProfile(compressedImage);
			docrepo.save(doctor);
			return "Uploaded user image successfully";

		} catch (IOException e) {
			throw new RuntimeException("Failed to read the image data", e);
		}
	}




	public String getPictureFormat(int id){
		DoctorEntity doctor = docrepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Doctor " + id + " does not exist"));
		String pictureName = doctor.getProfilePictureName();
		return pictureName.substring(pictureName.lastIndexOf(".") + 1);
	}


	@Transactional(readOnly = true)
	public byte[] downloadDoctorImage(int id){
		DoctorEntity doctor = docrepo.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Doctor " + id + " does not exist"));
		return ImageUtils.decompressImage(doctor.getDoctorProfile());
	}
}
