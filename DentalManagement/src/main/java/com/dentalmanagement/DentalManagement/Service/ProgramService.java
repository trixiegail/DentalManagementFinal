package com.dentalmanagement.DentalManagement.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.dentalmanagement.DentalManagement.Entity.ProgramEntity;
import com.dentalmanagement.DentalManagement.Repository.ProgramRepository;

@Service
public class ProgramService {

	@Autowired
	ProgramRepository progrepo;
	
	//add a program
	public ProgramEntity insertProgram(ProgramEntity program) {
		return progrepo.save(program);
	}
	
	//get all program
	public List<ProgramEntity> getAllProgram(){
		return progrepo.findAll();
	}
	
	//update a program
	@SuppressWarnings("finally")
	public ProgramEntity updateProgram(long id, ProgramEntity newProgramDetails) {
		ProgramEntity prog = new ProgramEntity();
		
		try {
			prog = progrepo.findById(id).get();
		}catch(NoSuchElementException ex){
			throw new NoSuchElementException("Program does not exist");
		}finally {
			return progrepo.save(prog);
		}
	}
	
	//delete a program
	public String deleteProgram(@PathVariable long id) {
		String msg = "";
		
		if(progrepo.findById((long) id) != null) {
			progrepo.deleteById(id);
			msg = "Program is successfully deleted";
		}
		return msg;
	}
	
	  //search for department based on letters typed
    public List<ProgramEntity> searchProgram(String keyword) {
        // Search for department who contains the keyword
        return progrepo.findByProgramOrProgramCode(keyword, keyword);
    }
}
