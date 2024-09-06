package com.dentalmanagement.DentalManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;
    private int students;
    private int slotsLeft;
    
	public Schedule() {
		super();
	}

	public Schedule(String date, String time, int students, int slotsLeft) {
		super();
		this.date = date;
		this.time = time;
		this.students = students;
		this.slotsLeft = slotsLeft;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}

	public int getSlotsLeft() {
		return slotsLeft;
	}

	public void setSlotsLeft(int slotsLeft) {
		this.slotsLeft = slotsLeft;
	}
	
}

