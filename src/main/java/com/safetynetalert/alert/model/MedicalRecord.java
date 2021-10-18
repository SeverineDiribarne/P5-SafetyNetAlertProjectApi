package com.safetynetalert.alert.model;

import java.util.List;

public class MedicalRecord {
	
	//Attributes
	private List<String> medication;
	private List<String> allergies;

	/**
	 * getter medication
	 * @return this medication
	 */
	public List<String> getMedication() {
		return this.medication;
	}

	/**
	 * setter medication
	 * @param medication
	 */
	public void setMedication(List<String> medication) {
		this.medication = medication;
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

	/**
	 * ToString method
	 */
	public String toString() {
		return "  Medications : " + getMedication() + 
				"  Allergies : " + getAllergies();
	}
}
