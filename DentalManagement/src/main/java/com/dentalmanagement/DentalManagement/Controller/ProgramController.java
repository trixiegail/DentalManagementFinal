package com.dentalmanagement.DentalManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dentalmanagement.DentalManagement.Entity.ProgramEntity;
import com.dentalmanagement.DentalManagement.Service.ProgramService;

@RestController
@RequestMapping("/program")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProgramController {
	@Autowired
	ProgramService progserv;
	
	//add a program
	@PostMapping("/insertProgram")
	public ProgramEntity insertProgram(@RequestBody ProgramEntity program) {
		return progserv.insertProgram(program);
	}
	
	//get all program
	@GetMapping("/getAllProgram")
	public List<ProgramEntity> getAllPrograms(){
		return progserv.getAllProgram();
	}
	
	//update a program
	@PutMapping("/updateProgram")
	public ProgramEntity updateProgram(@RequestParam long id, @RequestBody ProgramEntity newProgramDetails) {
		return progserv.updateProgram(id, newProgramDetails);
	}
	
	//delete a program
	@DeleteMapping("/deleteProgram/{id}")
	public String deleteProgram(@PathVariable long id) {
		return progserv.deleteProgram(id);
	}
	
	@GetMapping("/search")
	public List<ProgramEntity> searchProgram(@RequestParam String keyword) {
	     return progserv.searchProgram(keyword);
	}
}
