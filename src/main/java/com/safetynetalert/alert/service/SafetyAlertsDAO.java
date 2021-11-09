package com.safetynetalert.alert.service;

import java.util.List;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;

public interface SafetyAlertsDAO {

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

	/**
	 * add persons
	 * @param person
	 * @return person added
	 */
	boolean addPersons(Person person);

	/**
	 * Update person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	boolean updatePerson(String firstName, String lastName, Person person);

	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 */
	boolean deletePerson(String firstName, String lastName);

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	boolean addMapping(Integer firestationId, String address);

	/**
	 * Update the fireStation number based on an address
	 * @param firestationId
	 * @param address
	 */
	boolean updateFirestationNumber(Integer oldFirestationId, Integer newfirestationId, String address);

	/**
	 * Delete mapping by firestationId
	 * @param firestationId
	 */
	boolean deleteMappingByFirestationId(Integer firestationId);

	/**
	 *  Delete mapping by address
	 * @param address
	 */
	boolean deleteMappingByAddress(String address);

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