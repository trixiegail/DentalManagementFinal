package com.dentalmanagement.DentalManagement.Entity;

import java.util.Date;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tooth_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ToothStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tooth_number")
    private int toothNumber;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @Column(name = "student_number")
    private String studentNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date")
    private Date savedAt;


    public ToothStatus() {}

    public ToothStatus(int toothNumber, String status, StudentEntity student) {
        this.toothNumber = toothNumber;
        this.status = status;
        this.studentNumber = student.getIdNumber();
        this.student = student;
        this.savedAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToothNumber() {
        return toothNumber;
    }

    public void setToothNumber(int toothNumber) {
        this.toothNumber = toothNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
        this.studentNumber = student.getIdNumber();
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }
}