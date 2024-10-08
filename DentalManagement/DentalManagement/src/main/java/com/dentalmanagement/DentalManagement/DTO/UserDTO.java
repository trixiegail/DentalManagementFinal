package com.dentalmanagement.DentalManagement.DTO;

import com.dentalmanagement.DentalManagement.Entity.OtherUserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDTO {

    private String idNumber;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String gender;
    private String email;
    private String password;
    private boolean archived;

    @Enumerated(EnumType.STRING)
    private OtherUserRole role;

	public UserDTO() {
		super();
	}

	public UserDTO(String idNumber, String firstname, String lastname, String birthdate, String gender, String email,
			String password, boolean archived, OtherUserRole role) {
		super();
		this.idNumber = idNumber;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.archived = archived;
		this.role = role;
	}

	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public OtherUserRole getRole() {
		return role;
	}

	public void setRole(OtherUserRole role) {
		this.role = role;
	}
}

