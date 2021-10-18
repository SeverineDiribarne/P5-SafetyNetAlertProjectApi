package com.safetynetalert.alert.repository.entities;

import java.util.List;

public class MedicalRecordsEntity {
	
	// Attributes
	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies;

	/**
	 * Getter firstName
	 * @return this firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setter firstName
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter lastName
	 * @return this lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setter lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter birthDate
	 * @return this birthDate
	 */
	public String getBirthdate() {
		return this.birthdate;
	}

	/**
	 * Setter birthDate
	 * @param birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Getter medications
	 * @return this.medications
	 */
	public List<String> getMedications() {
		return this.medications;
	}

	/**
	 * Setter medications
	 * @param medications
	 */
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	/**
	 * getter allergies
	 * @return this allergies
	 */
	public List<String> getAllergies() {
		return this.allergies;
	}

	/**
	 * Setter allergies
	 * @param allergies
	 */
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
}
