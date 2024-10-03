	package com.dentalmanagement.DentalManagement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dentalrecord")
public class DentalRecordEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "bp")
	private String bloodPressure;
	
	@Column(name = "heart_rate")
	private String heartRate;
	
	@Column(name = "temp")
	private String temperature;
	
	@Column(name = "oral_health_status")
	private String oralHealthStatus;
	
	@Column(name = "cavities")
	private String cavities;
	
	@Column(name = "gum_health")
	private String gumHealth;
	
	@Column(name = "general_health")
	private String generalHealth;
	
	@Column(name = "other_concer")
	private String otherConcern;

	public DentalRecordEntity() {
		super();
	}

	public DentalRecordEntity(String bloodPressure, String heartRate, String temperature, String oralHealthStatus,
			String cavities, String gumHealth, String generalHealth, String otherConcern) {
		super();
		this.bloodPressure = bloodPressure;
		this.heartRate = heartRate;
		this.temperature = temperature;
		this.oralHealthStatus = oralHealthStatus;
		this.cavities = cavities;
		this.gumHealth = gumHealth;
		this.generalHealth = generalHealth;
		this.otherConcern = otherConcern;
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

	public String getCavities() {
		return cavities;
	}

	public void setCavities(String cavities) {
		this.cavities = cavities;
	}

	public String getGumHealth() {
		return gumHealth;
	}

	public void setGumHealth(String gumHealth) {
		this.gumHealth = gumHealth;
	}

	public String getGeneralHealth() {
		return generalHealth;
	}

	public void setGeneralHealth(String generalHealth) {
		this.generalHealth = generalHealth;
	}

	public String getOtherConcern() {
		return otherConcern;
	}

	public void setOtherConcern(String otherConcern) {
		this.otherConcern = otherConcern;
	}
	
}
