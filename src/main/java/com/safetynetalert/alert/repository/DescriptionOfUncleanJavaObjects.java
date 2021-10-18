package com.safetynetalert.alert.repository;

import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;
import java.util.List;

public class DescriptionOfUncleanJavaObjects {
	
	//Attributes
	private List<PersonsEntity> persons;
	private List<FirestationsEntity> firestations;
	private List<MedicalRecordsEntity> medicalrecords;

	/**
	 * getter persons entity
	 * @return this persons
	 */
	public List<PersonsEntity> getPersons() {
		return this.persons;
	}

	/**
	 * setter persons entity
	 * @param persons
	 */
	public void setPersonsEntities(List<PersonsEntity> persons) {
		this.persons = persons;
	}

	/**
	 * getter firestations entity 
	 * @return this firestations
	 */
	public List<FirestationsEntity> getFirestations() {
		return this.firestations;
	}

	/**
	 * Setter firestations entity
	 * @param firestations
	 */
	public void setFireStationsEntities(List<FirestationsEntity> firestations) {
		this.firestations = firestations;
	}

	/**
	 * getter medicalrecords entity
	 * @return this medicalrecords
	 */
	public List<MedicalRecordsEntity> getMedicalrecords() {
		return this.medicalrecords;
	}

	/**
	 * setter medicalrecords entity
	 * @param medicalrecords
	 */
	public void setMedicalrecordsEntities(List<MedicalRecordsEntity> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}
}
