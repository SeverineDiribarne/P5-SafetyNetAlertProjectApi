package com.safetynetalert.alert.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RepositoryMedicalRecord;
@Service
public class SafetyAlertsInMemoryDAOMedicalRecord implements SafetyAlertsDAOMedicalRecord {

	RepositoryMedicalRecord repository = new RepositoryMedicalRecord();
	
	private static final Logger log = LogManager.getLogger(); 
	
	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return 
	 * @return medicalRecord added
	 */
	@Override
	public boolean addMedicalRecord(int personId, MedicalRecord medicalRecord) {
		if(personId != 0 && medicalRecord != null) {
		log.debug("return the state of adding of the medical file of the person given in parameter");
		return repository.addMedicalRecord(personId, medicalRecord );
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
	@Override
	public boolean updateMedicalRecord(String firstName, String lastName, Person person) {
		if(firstName != null && lastName != null && person != null) {
		log.debug("return the state of modification of the medical file of the person given in parameter");
		 return repository.updateMedicalRecord(firstName, lastName, person);
		}
		log.debug("return false : the state of modification of the medical file of the person given in parameter isn't carried out");
		return false;
	}
	
	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	@Override
	public boolean deleteMedicalRecord(String firstName, String lastName) {
		if(firstName != null && lastName != null) {
		log.debug("return the state of deletion of the medical file of the person given in parameter");
		return repository.deleteMedicalRecord(firstName, lastName);
		}
		log.debug("return false : the state of deletion of the medical file of the person given in parameter isn't carried out");
		return false;
	}
}
