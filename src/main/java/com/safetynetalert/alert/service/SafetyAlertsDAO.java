package com.safetynetalert.alert.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;

@Service
public class SafetyAlertsDAO {

	private static final Logger log = LogManager.getLogger(); 

	/**
	 * get persons
	 * @return persons list
	 */
	public List<Person> getPersons() {
		log.debug("returns the list of persons");
		return RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		
	}
	
	/**
	 * get fireStations
	 * @return fireStation list
	 */
	public List<FireStation> getFireStations() {
		log.debug("returns the list of fireStations");
		return RecoveryOfJsonDataInJavaObject.getInstance().getFirestations();
	}

	/**
	 * get Medical records
	 * @return medicalRecords list
	 */
	public List<MedicalRecord> getMedicalRecords() {
		log.debug("returns the list of medicalRecords");
		return RecoveryOfJsonDataInJavaObject.getInstance().getMedicalrecords();
	}
	
	//-----------------------------------------/person------------------------------------------------------------------
	
	/**
	 * add persons
	 * @param person
	 * @return person added
	 */
	public boolean addPersons(Person person) {
		if(person != null) {
		log.debug("return the state of adding of the person with request body given.");
		return RecoveryOfJsonDataInJavaObject.getInstance().addPerson(person);
		}
		log.debug("return the state of adding of the person with request body given isn't carried out");
		return false;
	}

	/**
	 * Update person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public boolean updatePerson( String firstName, String lastName, Person person) {
		if(firstName != null && lastName!= null && person !=null) {
		log.debug("return the state of updating of the person found of the firstName/lastName given in parameter");
		 return RecoveryOfJsonDataInJavaObject.getInstance().updatePerson(firstName, lastName, person);
		}
		log.debug("return the state of updating of the person found of the firstName/lastName given in parameter isn't carried out");
		return false;
	}

	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 */
	public boolean deletePerson(String firstName, String lastName) {
		if(firstName != null && lastName != null) {
		log.debug("return the state of deletion of the person found of the firstName/lastName given in parameter");
		return RecoveryOfJsonDataInJavaObject.getInstance().deletePerson(firstName, lastName);
		}
		log.debug("return false : the state of deletion of the person found of the firstName/lastName given in parameter isn't carried out");
		return false;
	}

	//---------------------------------------/firestation--------------------------------------------------------------
	
	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	public boolean addMapping(Integer firestationId, String address) {
		if(firestationId !=0 && address != null) {
		log.debug("return the state of adding of the mapping of the firestationId/address given in parameter");
		 return RecoveryOfJsonDataInJavaObject.getInstance().addMapping(firestationId, address);
		}
		log.debug("return false : the state of adding of the mapping of the firestationId/address given in parameter isn't carried out");
		return false;
	}
	
	/**
	 * Update the fireStation number based on an address
	 * @param firestationId
	 * @param address
	 */
	public boolean updateFirestationNumber(Integer oldFirestationId,Integer newfirestationId, String address) {
		if(oldFirestationId != 0 && newfirestationId != 0 && address != null) {
		log.debug("return the state of modification of firestationId number of the address given in parameter");
		 return RecoveryOfJsonDataInJavaObject.getInstance().updateFirestationNumber(oldFirestationId, newfirestationId, address);
		}
		log.debug("return false : the state of modification of firestationId number of the address given in parameter isn't carried out");
		return false;
	}
	/**
	 * Delete mapping by firestationId
	 * @param firestationId
	 */
	public boolean deleteMappingByFirestationId(Integer firestationId ) {
		//Delete the mapping by fireStationId
		if(firestationId != null) {
			log.debug("return the state of deletion of the mapping of the firestationId given in parameter");
			return RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByFirestationId(firestationId);	
		}
		log.debug("return false : if the state of deletion of the mapping of the firestationId given in parameter isn't carried out");
		return false;
	}
	/**
	 *  Delete mapping by address
	 * @param address
	 */
		public boolean deleteMappingByAddress(String address) {
			//Delete the mapping by address
			if(address != null) {
				log.debug("returns the state of deletion of the mapping of the address given in parameter");
				 return RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByAddress(address);	
			}
			log.debug("return false : if the state of deletion of the mapping of the address given in parameter isn't carried out");
			return false;
		}
	
	//-------------------------------------/medicalRecord--------------------------------------------------------------
	
	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return 
	 * @return medicalRecord added
	 */
	public boolean addMedicalRecord(int personId, MedicalRecord medicalRecord) {
		if(personId != 0 && medicalRecord != null) {
		log.debug("return the state of adding of the medical file of the person given in parameter");
		return RecoveryOfJsonDataInJavaObject.getInstance().addMedicalRecord(personId, medicalRecord );
		}
		log.debug("return false : if the state of adding of the medical file of the person given in parameter isn't carried out");
		return false;
	}

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public boolean updateMedicalRecord(String firstName, String lastName, Person person) {
		if(firstName != null && lastName != null && person != null) {
		log.debug("return the state of modification of the medical file of the person given in parameter");
		 return RecoveryOfJsonDataInJavaObject.getInstance().updateMedicalRecord(firstName, lastName, person);
		}
		log.debug("return false : the state of modification of the medical file of the person given in parameter isn't carried out");
		return false;
	}
	
	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	public boolean deleteMedicalRecord(String firstName, String lastName) {
		if(firstName != null && lastName != null) {
		log.debug("return the state of deletion of the medical file of the person given in parameter");
		return RecoveryOfJsonDataInJavaObject.getInstance().deleteMedicalRecord(firstName, lastName);
		}
		log.debug("return false : the state of deletion of the medical file of the person given in parameter isn't carried out");
		return false;
	}
}
