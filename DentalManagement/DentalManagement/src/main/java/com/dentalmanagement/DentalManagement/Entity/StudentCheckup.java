package com.dentalmanagement.DentalManagement.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class StudentCheckup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodPressure;
    private String heartRate;
    private String respiratoryRate;
    private String temperature;
    private String oralHealthStatus;
    private String gumHealth;
    private String presenceOfCavities;
    private String generalHealthCondition;
    private String specificHealthConcerns;
    private LocalDateTime date;
    private String studentIdNumber;
    
//    @ManyToOne
//    @JoinColumn(name = "student_id", nullable = false) // This column must be set before saving
//    private StudentEntity student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore  // Prevent recursive reference
    private StudentEntity student;
    

    // Constructors
    public StudentCheckup() {}

    public StudentCheckup(String bloodPressure, String heartRate, String respiratoryRate, String temperature, 
                          String oralHealthStatus, String gumHealth, String presenceOfCavities, 
                          String generalHealthCondition, String specificHealthConcerns) {
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.temperature = temperature;
        this.oralHealthStatus = oralHealthStatus;
        this.gumHealth = gumHealth;
        this.presenceOfCavities = presenceOfCavities;
        this.generalHealthCondition = generalHealthCondition;
        this.specificHealthConcerns = specificHealthConcerns;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(String respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOralHealthStatus() {
        return oralHealthStatus;
    }

    public void setOralHealthStatus(String oralHealthStatus) {
        this.oralHealthStatus = oralHealthStatus;
    }

    public String getGumHealth() {
        return gumHealth;
    }

    public void setGumHealth(String gumHealth) {
        this.gumHealth = gumHealth;
    }

    public String getPresenceOfCavities() {
        return presenceOfCavities;
    }

    public void setPresenceOfCavities(String presenceOfCavities) {
        this.presenceOfCavities = presenceOfCavities;
    }

    public String getGeneralHealthCondition() {
        return generalHealthCondition;
    }

    public void setGeneralHealthCondition(String generalHealthCondition) {
        this.generalHealthCondition = generalHealthCondition;
    }

    public String getSpecificHealthConcerns() {
        return specificHealthConcerns;
    }

    public void setSpecificHealthConcerns(String specificHealthConcerns) {
        this.specificHealthConcerns = specificHealthConcerns;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student; 
    }
    
    public String getStudentIdNumber() {
        return studentIdNumber;
    }

    public void setStudentIdNumber(String studentIdNumber) {
        this.studentIdNumber = studentIdNumber;
    }
}
