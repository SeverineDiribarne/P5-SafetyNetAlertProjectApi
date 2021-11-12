package com.safetynetalert.alert.service;

import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;

public interface SafetyAlertsDAOMedicalRecord {

	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return 
	 * @return medicalRecord added
	 */
	boolean addMedicalRecord(int personId, MedicalRecord medicalRecord);

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	boolean updateMedicalRecord(String firstName, String lastName, Person person);

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	boolean deleteMedicalRecord(String firstName, String lastName);

}