package com.dentalmanagement.DentalManagement.Service;

import com.dentalmanagement.DentalManagement.Entity.CompletedAppointment;
import com.dentalmanagement.DentalManagement.Entity.DeclinedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Repository.CompletedAppointmentRepository;
import com.dentalmanagement.DentalManagement.Repository.DeclinedAppointmentRepository;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;
import com.dentalmanagement.DentalManagement.Repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CompletedAppointmentService {

	 @Autowired
	    private CompletedAppointmentRepository completedAppointmentRepository;
	    
	    @Autowired  // Missing this annotation for ReservationRepository
	    private ReservationRepository reservationRepository;
	    
	    @Autowired
	    private PatientRepository patientRepository;

	    // Fetch a reservation by ID
	    public Patient getPatient(Long id) {
	        return patientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Patient not found"));
	    }

	    // Save a completed appointment
	    public CompletedAppointment saveCompletedAppointment(CompletedAppointment completedAppointment) {
	        return completedAppointmentRepository.save(completedAppointment);
	    }

	    // Move a reservation to completed appointments
	    public void moveToCompletedAppointments(Patient patient) {
	        // Create a new CompletedAppointment object and transfer data
	        CompletedAppointment completedAppointment = new CompletedAppointment();
	        completedAppointment.setFullName(patient.getFullName());
	        completedAppointment.setStudentIdNumber(patient.getStudentIdNumber());
	        completedAppointment.setProgram(patient.getProgram());
	        completedAppointment.setYearLevel(patient.getYearLevel());
	        completedAppointment.setDate(patient.getDate()); 
	        completedAppointment.setTime(patient.getTime());
	        completedAppointment.setCompletedDate(LocalDateTime.now()); // Set current date/time for completion

	        // Save the completed appointment
	        completedAppointmentRepository.save(completedAppointment);

	        // Optionally, delete the patient from the repository if needed
	        patientRepository.deleteById(patient.getId());
	    }

	    // Fetch all completed appointments
	    public List<CompletedAppointment> getAllCompletedAppointments() {
	        return completedAppointmentRepository.findAll();
	    }

	    // Delete a completed appointment by ID
	    public boolean deleteCompletedAppointment(Long id) {
	        if (completedAppointmentRepository.existsById(id)) {
	            completedAppointmentRepository.deleteById(id);
	            return true;
	        } else {
	            return false;
	        }
	    }

	    // Fetch reservations by student ID
	    public List<Patient> getPatientByStudentId(String studentIdNumber) {
	        return patientRepository.findByStudentIdNumber(studentIdNumber);
	    }
	}