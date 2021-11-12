package com.safetynetalert.alert.service;

import java.util.List;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;

public interface SafetyAlertsDAO  {

	/**
	 * get persons
	 * @return persons list
	 */
	List<Person> getPersons();

	/**
	 * get fireStations
	 * @return fireStation list
	 */
	List<FireStation> getFireStations();

	/**
	 * get Medical records
	 * @return medicalRecords list
	 */
	List<MedicalRecord> getMedicalRecords();

}