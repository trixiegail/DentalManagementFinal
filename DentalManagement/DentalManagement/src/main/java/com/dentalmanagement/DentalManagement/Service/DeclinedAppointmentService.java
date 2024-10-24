package com.dentalmanagement.DentalManagement.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentalmanagement.DentalManagement.Entity.DeclinedAppointment;
import com.dentalmanagement.DentalManagement.Entity.Patient;
import com.dentalmanagement.DentalManagement.Entity.Reservation;
import com.dentalmanagement.DentalManagement.Repository.DeclinedAppointmentRepository;
import com.dentalmanagement.DentalManagement.Repository.PatientRepository;
import com.dentalmanagement.DentalManagement.Repository.ReservationRepository;

@Service
public class DeclinedAppointmentService {

    @Autowired
    private DeclinedAppointmentRepository declinedAppointmentRepository;
    
    @Autowired  // Missing this annotation for ReservationRepository
    private ReservationRepository reservationRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    // Fetch a reservation by ID
    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation not found"));
    }

    // Save declined appointment
    public DeclinedAppointment saveDeclinedAppointment(DeclinedAppointment declinedAppointment) {
        return declinedAppointmentRepository.save(declinedAppointment);
    }

    // Move reservation data to DeclinedAppointments
    public void moveToDeclinedAppointments(Reservation reservation) {
        System.out.println("moveToDeclinedAppointments called for: " + reservation.getStudentIdNumber());

        DeclinedAppointment declinedAppointment = new DeclinedAppointment();
        declinedAppointment.setStudentIdNumber(reservation.getStudentIdNumber());
        declinedAppointment.setFullName(reservation.getFullName());
        declinedAppointment.setProgram(reservation.getProgram());
        declinedAppointment.setYearLevel(reservation.getYearLevel());
        declinedAppointment.setDate(reservation.getDate());
        declinedAppointment.setTime(reservation.getTime());
        declinedAppointment.setDeclinedDate(LocalDateTime.now()); 

        // Save to the repository
        declinedAppointmentRepository.save(declinedAppointment);
    }
    
    // Fetch all declined appointments
    public List<DeclinedAppointment> getAllDeclinedAppointments() {
        return declinedAppointmentRepository.findAll();
    }

    // Delete a declined appointment by ID
    public boolean deleteDeclinedAppointment(Long id) {
        if (declinedAppointmentRepository.existsById(id)) {
            declinedAppointmentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean moveToDeclinedAppointments(Long id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            DeclinedAppointment patient = new DeclinedAppointment();

            // Transfer reservation details to patient
            patient.setFullName(reservation.getFullName());
            patient.setStudentIdNumber(reservation.getStudentIdNumber());
            patient.setProgram(reservation.getProgram());
            patient.setYearLevel(reservation.getYearLevel());
            patient.setDate(reservation.getDate()); 
            patient.setTime(reservation.getTime());

            // Save the patient to the patient repository
            declinedAppointmentRepository.save(patient);

            // Delete the reservation from the repository
            declinedAppointmentRepository.deleteById(id);

            return true;
        } else {
            throw new NoSuchElementException("Reservation not found");
        }
    }

    public List<Reservation> getReservationsByStudentId(String studentIdNumber) {
        return reservationRepository.findByStudentIdNumber(studentIdNumber);
    }
}
