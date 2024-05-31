package com.dentalmanagement.DentalManagement.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.DepartmentEntity;
import com.dentalmanagement.DentalManagement.Repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	DepartmentRepository deptrepo;
	
	//add a department
	public DepartmentEntity insertDepartment(DepartmentEntity dept) {
		return deptrepo.save(dept);
	}
	
	//get all department
	public List<DepartmentEntity> getAllDepartment(){
		return deptrepo.findAll();
	}
	
	//update a department
	@SuppressWarnings("finally")
	public DepartmentEntity updateDepartment(long id, DepartmentEntity newDepartmentDetails) {
		DepartmentEntity dept = new DepartmentEntity();
		
		try {
			dept = deptrepo.findById(id).get();
			
			dept.setDepartment(newDepartmentDetails.getDepartment());
			dept.setDeptCode(newDepartmentDetails.getDeptCode());
		}catch(NoSuchElementException ex){
			throw new NoSuchElementException("Student "+id+" does not exist");
		}finally {
			return deptrepo.save(dept);
		}
	}
	
	//delete a department
	public String deleteDepartment(long id) {
		String msg = "";
		
		if(deptrepo.findById((long) id) != null) {
			deptrepo.deleteById((long) id);
			msg = "Student " + id + " is successfully deleted";
		}
		return msg;
	}
	
	  //search for department based on letters typed
    public List<DepartmentEntity> searchDepartment(String keyword) {
        // Search for department who contains the keyword
        return deptrepo.findByDepartmentOrDepartmentCode(keyword, keyword);
    }

}
