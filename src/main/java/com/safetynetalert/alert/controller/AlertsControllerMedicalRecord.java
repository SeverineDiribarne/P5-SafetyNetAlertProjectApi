package com.safetynetalert.alert.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsDAOMedicalRecord;
@RestController
public class AlertsControllerMedicalRecord {
	
	@Autowired
	public SafetyAlertsDAOMedicalRecord safetyAlertsDaoMedicalRecord;

	private static final Logger log = LogManager.getLogger(); 
	
	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return medicalRecord
	 */
	@PostMapping("/medicalRecord")
	public void addAMedicalRecord(@RequestParam int personId, @RequestBody MedicalRecord medicalRecord ) {
		log.info("Request to add a medical record to a person with input parameter: personId {}, and request body: medicalRecord {}", personId, medicalRecord);
		boolean result = safetyAlertsDaoMedicalRecord.addMedicalRecord(personId, medicalRecord );
		if( result )
		{
			log.info("Answer sent: The medical file has been added to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be added to the requested person.");
		}
	}
	/**
	 * Update an existing MedicalRecord of a specific person 
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	@PutMapping("/medicalRecord")
	public void updateAnExistingMedicalRecord(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,@RequestBody Person person) {
		log.info("Change request with input parameters: firstName {}, lastName {}, and request body: person {}", firstName, lastName, person);
		boolean result = safetyAlertsDaoMedicalRecord.updateMedicalRecord(firstName, lastName, person);
		if( result )
		{
			log.info("Answer sent: The medical file has been updated to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be updated to the requested person.");
		}
	}

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	@DeleteMapping("/medicalRecord")
	public void deleteAPersonSMedicalRecord(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName) {
		log.info("Delete request with input parameters: firstName {}, lastName {}", firstName, lastName);
		boolean result = safetyAlertsDaoMedicalRecord.deleteMedicalRecord(firstName, lastName);
		if( result )
		{
			log.info("Answer sent: The medical file has been deleted to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be deleted to the requested person.");
		}
	}
}