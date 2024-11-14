package com.dentalmanagement.DentalManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="tablestaff")
public class StaffEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "id_number")
	private String idNumber;
	
	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastname;
	
	@Column(name = "birthdate")
	private String birthdate;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;

	@Column(name = "archived_accounts")
	private boolean archived;

//	@OneToOne(mappedBy = "staff", cascade = CascadeType.ALL)
//	private ForgotPassword forgotPassword
	
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private OtherUserRole role;

	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "LONGBLOB")
	private byte[]staffProfile;

	private String profilePictureName;

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName){
		this.profilePictureName = profilePictureName;
	}

	public byte[] getStaffProfile() {
		return staffProfile;
	}

	public void setStaffProfile(byte[] staffProfile) {
		this.staffProfile = staffProfile;
	}

	public StaffEntity() {
		super();
	}

	public StaffEntity(String idNumber, String firstname, String lastname, String birthdate, String gender,
			String email, String password, boolean archived, OtherUserRole role) {
		super();
		this.idNumber = idNumber;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.archived = archived;
//		this.role = role;
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
	
	public void setArchived(boolean archive) {
		this.archived = archive;
	}
//	public OtherUserRole getRole() {
//		return role;
//	}
//
//
//	public void setRole(OtherUserRole role) {
//		this.role = role;
//	}
	public long getId() {
        return id;
    }
}
