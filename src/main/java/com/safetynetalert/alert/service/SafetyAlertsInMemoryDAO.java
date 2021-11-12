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
public class SafetyAlertsInMemoryDAO implements SafetyAlertsDAO {

	private static final Logger log = LogManager.getLogger(); 

	/**
	 * get persons
	 * @return persons list
	 */
	@Override
	public List<Person> getPersons() {
		log.debug("returns the list of persons");
		return RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		
	}
	
	/**
	 * get fireStations
	 * @return fireStation list
	 */
	@Override
	public List<FireStation> getFireStations() {
		log.debug("returns the list of fireStations");
		return RecoveryOfJsonDataInJavaObject.getInstance().getFirestations();
	}

	/**
	 * get Medical records
	 * @return medicalRecords list
	 */
	@Override
	public List<MedicalRecord> getMedicalRecords() {
		log.debug("returns the list of medicalRecords");
		return RecoveryOfJsonDataInJavaObject.getInstance().getMedicalrecords();
	}
}
