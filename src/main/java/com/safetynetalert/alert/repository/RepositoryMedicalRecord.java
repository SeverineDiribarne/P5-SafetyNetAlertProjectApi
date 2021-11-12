package com.safetynetalert.alert.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;

public class RepositoryMedicalRecord {

	RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
	
	private static final Logger log = LogManager.getLogger(); 
	private static final String FOUND = "person found";
	private static final String NOTFOUND = "person not found";

	/**
	 * Get person by firstName and lastName
	 * @param firstName
	 * @param lastName
	 * @return person or null
	 */
	private Person getPersonByFirstNameAndLastName(String firstName , String lastName) {
		log.debug("Search for the person by first and last name");
		for (Person person : repository.getPersons()) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				log.debug(FOUND);
				return person;
			}
		}
		log.debug(NOTFOUND);
		return null;
	}
	
	/**
	 * Get person by Id
	 * @param personId
	 * @return person or null
	 */
	private Person getPersonById(int personId) {
		for (Person person : repository.getPersons()) {
			if (person.getId()==(personId)) {
				log.debug(FOUND);
				return person;
			}
		}
		log.debug(NOTFOUND);
		return null;
	}

	/**
	 * Add a medical record
	 * @param personId
	 * @param medicalRecord
	 * @return true or false
	 */
	public boolean addMedicalRecord(int personId,MedicalRecord medicalRecord ) {
		Person personFound = getPersonById(personId);
		if(personFound !=null) {
			log.debug("Return true : the medical record has been added to the person found");
			personFound.setMedicalRecord(medicalRecord);
			return true;
		}
		log.debug(" return false : the medical record hasn't been added to the person found");
		return false;
	}

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 * @return true or false
	 */
	public boolean updateMedicalRecord(String firstName, String lastName, Person person) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			log.debug("The medical record has been updated to the person found");
			personFound.setMedicalRecord(person.getMedicalRecord());
			return true;
		}
		log.debug("The medical record hasn't been updated to the person found");
		return false;	
	}

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 * @return true or false
	 */
	public boolean deleteMedicalRecord(String firstName, String lastName) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			log.debug("The medical record has been deleted to the person found");
			personFound.getMedicalRecord().setMedication(null);
			personFound.getMedicalRecord().setAllergies(null);
			return true;
		}
		log.debug("The medical record hasn't been deleted to the person found");
		return false;
	}
}
