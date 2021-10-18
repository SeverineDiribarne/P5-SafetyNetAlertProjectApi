package com.safetynetalert.alert.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;

@Service
public class SafetyAlertsDAO {

	/**
	 * get persons
	 * @return persons list
	 */
	public List<Person> getPersons() {
		return RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
	}
	
	/**
	 * get fireStations
	 * @return fireStation list
	 */
	public List<FireStation> getFireStations() {
		return RecoveryOfJsonDataInJavaObject.getInstance().getFirestations();
	}

	/**
	 * get Medical records
	 * @return medicalRecords list
	 */
	public List<MedicalRecord> getMedicalRecords() {
		return RecoveryOfJsonDataInJavaObject.getInstance().getMedicalrecords();
	}
	
	//-----------------------------------------/person------------------------------------------------------------------
	
	/**
	 * add persons
	 * @param person
	 * @return person added
	 */
	public Person addPersons(Person person) {
		return RecoveryOfJsonDataInJavaObject.getInstance().addPerson(person);
	}

	/**
	 * Update person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public void updatePerson( String firstName, String lastName, Person person) {
		 RecoveryOfJsonDataInJavaObject.getInstance().updatePerson(firstName, lastName, person);
	}

	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 */
	public void deletePerson(String firstName, String lastName) {
		RecoveryOfJsonDataInJavaObject.getInstance().deletePerson(firstName, lastName);
	}

	//---------------------------------------/firestation--------------------------------------------------------------
	
	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	public void addMapping(Integer firestationId, String address) {
		 RecoveryOfJsonDataInJavaObject.getInstance().addMapping(firestationId, address);
	}
	
	/**
	 * Update the fireStation number based on an address
	 * @param firestationId
	 * @param address
	 */
	public void updateFirestationNumber(Integer oldFirestationId,Integer newfirestationId, String address) {
		 RecoveryOfJsonDataInJavaObject.getInstance().updateFirestationNumber(oldFirestationId, newfirestationId, address);
	}
	/**
	 * Delete mapping by firestationId
	 * @param firestationId
	 */
	public void deleteMappingByFirestationId(Integer firestationId ) {
		//Delete the mapping by fireStationId
		if(firestationId != null) {
		 RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByFirestationId(firestationId);	
		}
	}
	/**
	 *  Delete mapping by address
	 * @param address
	 */
		public void deleteMappingByAddress(String address) {
			//Delete the mapping by address
			if(address != null) {
				 RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByAddress(address);	
			}	
		}
	
	//-------------------------------------/medicalRecord--------------------------------------------------------------
	
	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return medicalRecord added
	 */
	public void addMedicalRecord(int personId, MedicalRecord medicalRecord) {
		RecoveryOfJsonDataInJavaObject.getInstance().addMedicalRecord(personId, medicalRecord );
	}

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public void updateMedicalRecord(String firstName, String lastName, Person person) {
		 RecoveryOfJsonDataInJavaObject.getInstance().updateMedicalRecord(firstName, lastName, person);
	}
	
	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	public void deleteMedicalRecord(String firstName, String lastName) {
		 RecoveryOfJsonDataInJavaObject.getInstance().deleteMedicalRecord(firstName, lastName);
	}
}
