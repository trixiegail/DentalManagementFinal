package com.dentalmanagement.DentalManagement.DTO;

import java.util.Date;

public class ToothStatusDTO {
    private int toothNumber;
    private String status;
    private Date savedAt;

    // Constructor
    public ToothStatusDTO(int toothNumber, String status, Date savedAt) {
        this.toothNumber = toothNumber;
        this.status = status;
        this.savedAt = savedAt;
    }

    // Getters and Setters
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

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }
}