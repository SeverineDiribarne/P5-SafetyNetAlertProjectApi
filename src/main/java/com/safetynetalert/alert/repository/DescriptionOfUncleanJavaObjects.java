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
	 * getter firestations entity 
	 * @return this firestations
	 */
	public List<FirestationsEntity> getFirestations() {
		return this.firestations;
	}

	/**
	 * getter medicalrecords entity
	 * @return this medicalrecords
	 */
	public List<MedicalRecordsEntity> getMedicalrecords() {
		return this.medicalrecords;
	}
}
