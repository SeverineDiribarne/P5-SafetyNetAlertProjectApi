package com.safetynetalert.alert.repository;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynetalert.alert.model.Person;

public class RepositoryPerson {

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
	 * Add a new person 
	 * @param person
	 * @return true or false
	 */
	public boolean addPerson(Person person) {
		if(person != null) {
			repository.getPersons().add(person);
			log.debug(" Return true : the person found is added of the list of persons");
			return true;
		}
		log.debug("Return false : the person found isn't added of the list of persons");
		return false;
	}

	/**
	 * updated Address
	 * @param person
	 * @param personFound
	 */
	private void updatedAddress(Person person, Person personFound) {
		if(person.getAddress()!=null) {
			if((person.getAddress().getStreet()!= null) && !(person.getAddress().getStreet().isEmpty())) {
				log.debug("Update the street field if given as a parameter ");
				personFound.getAddress().setStreet(person.getAddress().getStreet());
			}
			if((person.getAddress().getCity()!= null) && !(person.getAddress().getCity().isEmpty())) {
				log.debug("Update the city field if given as a parameter ");
				personFound.getAddress().setCity(person.getAddress().getCity());
			}
			if((person.getAddress().getZip()!= null) && !(person.getAddress().getZip().isEmpty())) {
				log.debug("Update the zip field if given as a parameter ");
				personFound.getAddress().setZip(person.getAddress().getZip());
			}
			if((person.getAddress().getFireStationIds()!=null)&& CollectionUtils.isNotEmpty(person.getAddress().getFireStationIds())) {
				log.debug("Update the fireStationIds field if given as a parameter");
				personFound.getAddress().setFireStationIds(person.getAddress().getFireStationIds());
			}
		}
	}

	/**
	 * updated Person informations
	 * @param person
	 * @param personFound
	 */
	private void updatedPersonInformations(Person person, Person personFound) {
		if((person.getBirthDate()!= null) && !(person.getBirthDate().isEmpty())) {
			log.debug("Update the birthDate field if given as a parameter");
			personFound.setBirthDate(person.getBirthDate());	
		}
		if((person.getEmail()!=null) && !(person.getEmail().isEmpty())) {
			log.debug("Update the email field if given as a parameter");
			personFound.setEmail(person.getEmail());
		}
		if((person.getPhone()!= null) && !(person.getPhone().isEmpty())) {
			log.debug("Update the phone field if given as a parameter");
			personFound.setPhone(person.getPhone());
		}
	}

	/**
	 * update Medical Record
	 * @param person
	 * @param personFound
	 */
	private void updatedMedicalRecord(Person person, Person personFound) {
		if(person.getMedicalRecord()!=null) {
			if((person.getMedicalRecord().getMedication() != null ) && CollectionUtils.isNotEmpty(person.getMedicalRecord().getMedication())) {
				log.debug("Update the medications field if given as a parameter");
				personFound.getMedicalRecord().setMedication(person.getMedicalRecord().getMedication());
			}
			if((person.getMedicalRecord().getAllergies() != null) && CollectionUtils.isNotEmpty(person.getMedicalRecord().getAllergies())) {
				log.debug("Update the allergies field if given as a parameter");
				personFound.getMedicalRecord().setAllergies(person.getMedicalRecord().getAllergies());
			}
		}
	}

	/**
	 * Update a person
	 * @param firstName
	 * @param lastName
	 * @param person
	 * @return true or false
	 */
	public boolean updatePerson(String firstName, String lastName, Person person) {
		if (firstName != null && lastName != null && person != null) {
			Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);
			log.debug("Update the person found with the fields passed in parameter");
			if(personFound != null) {
				updatedAddress(person, personFound);
				updatedPersonInformations(person, personFound);
				updatedMedicalRecord(person, personFound);
			}
			log.debug("Return true : the person found is updated of the list of persons");
			return true;
		}
		log.debug("Return false : the person found isn't updated of the list of persons");
		return false;
	}

	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 * @return true or false
	 */
	public boolean deletePerson(String firstName, String lastName) {
		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);
		if(personFound != null) {
			log.debug("Return true : the person found is deleted of the list of persons");
			repository.getPersons().remove(personFound);
			return true;
		}
		log.debug("Return false : the person found isn't deleted of the list of persons");
		return false;
	}
}
