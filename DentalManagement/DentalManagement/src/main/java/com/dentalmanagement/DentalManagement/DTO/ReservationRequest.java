package com.dentalmanagement.DentalManagement.DTO;

public class ReservationRequest {
    private String date;
    private String time;

    // Getters and Setters
    public ReservationRequest() {
        super();
    }
    public ReservationRequest(String date, String time) {
        super();
        this.date = date;
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
