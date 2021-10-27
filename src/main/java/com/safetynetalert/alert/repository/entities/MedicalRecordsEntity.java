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
	 * Getter lastName
	 * @return this lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Getter birthDate
	 * @return this birthDate
	 */
	public String getBirthdate() {
		return this.birthdate;
	}

	/**
	 * Getter medications
	 * @return this.medications
	 */
	public List<String> getMedications() {
		return this.medications;
	}

	/**
	 * getter allergies
	 * @return this allergies
	 */
	public List<String> getAllergies() {
		return this.allergies;
	}
}
