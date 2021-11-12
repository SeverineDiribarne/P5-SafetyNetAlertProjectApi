package com.safetynetalert.alert.repository;

import com.jsoniter.JsonIterator;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

public class RecoveryOfJsonDataInJavaObject {

	private static final Logger log = LogManager.getLogger(); 
	private static final String FOUND = "person found";
	private static final String NOTFOUND = "person not found";

	// singleton with constructor
	private static RecoveryOfJsonDataInJavaObject repository;

	public static RecoveryOfJsonDataInJavaObject getInstance() {
		if (repository == null) {
			repository = new RecoveryOfJsonDataInJavaObject(); 
		}
		return repository;
	}

	//Attributes
	private ArrayList<Person> persons = new ArrayList<>();
	private ArrayList<FireStation> firestations = new ArrayList<>();
	private ArrayList<MedicalRecord> medicalrecords = new ArrayList<>();


	/**
	 * Getter Persons
	 * @return this persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Getter fireStation
	 * @return fireStations
	 */
	public List<FireStation> getFirestations() {
		return this.firestations;
	}

	/**
	 * Getter MedicalRecords
	 * @return medicalRecords
	 */
	public List<MedicalRecord> getMedicalrecords() {
		return this.medicalrecords;
	}

	/**
	 * Initialise method : read JSON and retrieving JSON info
	 * @throws IOException
	 */
	public void initialize() {

		this.persons.clear();
		this.firestations.clear();
		this.medicalrecords.clear();
		try {
			File file = ResourceUtils.getFile("classpath:data.json");
			String content = Files.readString(file.toPath());
			DescriptionOfUncleanJavaObjects jsonDataInJavaObject = JsonIterator.deserialize(content, DescriptionOfUncleanJavaObjects.class);
			fillModelWithJsonDataObjects(jsonDataInJavaObject.getPersons(), jsonDataInJavaObject.getFirestations(), jsonDataInJavaObject.getMedicalrecords());

		}
		catch(IOException e) {
			log.error("Error converting json file to Java object", e);
		}

	}

	/**
	 * Fill model with JSON Data Object
	 * @param personsEntities
	 * @param fireStationsEntities
	 * @param medicalrecordsEntities
	 */
	private void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
		log.debug("Start of loading the json file into memory in the model");
		fillFirestationModel(fireStationsEntities); 
		fillPersonModel(personsEntities, fireStationsEntities); 
		fillMedicalRecordModel(medicalrecordsEntities);
	}

	/**
	 * @param medicalrecordsEntities
	 */
	private void fillMedicalRecordModel(List<MedicalRecordsEntity> medicalrecordsEntities) {
		//medicalrecordsEntities
		for (MedicalRecordsEntity medicalRecordEntity : medicalrecordsEntities) {
			MedicalRecord medicalRecord = new MedicalRecord();
			Person person = getPersonByFirstNameAndLastName(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName());
			if (person != null) {
				person.setBirthDate(medicalRecordEntity.getBirthdate());
				medicalRecord.setMedication(medicalRecordEntity.getMedications());
				medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
				person.setMedicalRecord(medicalRecord);
			} 
			this.medicalrecords.add(medicalRecord);
			log.debug(" . Add medicalRecord {} into model ", medicalRecord );
		}	
		log.debug("End of loading in memory of the json file in the model");
	}

	/**
	 * @param personsEntities
	 * @param fireStationsEntities
	 */
	private void fillPersonModel(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities) {
		int id = 0;

		//personsEntities
		for (PersonsEntity personEntity : personsEntities) {
			Person person = new Person();
			Address address = new Address();
			person.setFirstName(personEntity.getFirstName());
			person.setLastName(personEntity.getLastName());
			person.setEmail(personEntity.getEmail());
			person.setPhone(personEntity.getPhone());
			address.setStreet(personEntity.getAddress());
			address.setCity(personEntity.getCity());
			address.setZip(personEntity.getZip());
			person.setAddress(address);
			address.setFireStationIds(new ArrayList<>());

			for (FirestationsEntity fireStationEntity : fireStationsEntities) {
				if (person.getAddress().getStreet().equals(fireStationEntity.getAddress())) {
					int stationId = Integer.parseInt(fireStationEntity.getStation());
					address.addFirestationId(Integer.valueOf(stationId)); 
					for(FireStation firestation : firestations ) {
						if(firestation.getStationId()==stationId) {
							firestation.addAddress(address);
						}
					}
				}
			} 
			person.setId(id++);
			persons.add(person);
			log.debug(" . Add person {} into model", person );
		}
	}

	/**
	 * @param fireStationsEntities
	 */
	private void fillFirestationModel(List<FirestationsEntity> fireStationsEntities) {
		//fireStationsEntities
		for (FirestationsEntity fireStationEntity : fireStationsEntities) {
			FireStation fireStation = new FireStation();
			fireStation.setStationId(Integer.parseInt(fireStationEntity.getStation()));
			this.firestations.add(fireStation);
			log.debug(" . Add fireStation {} into model", fireStation);
		}
	}

	/**
	 * Get person by firstName and lastName
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	private Person getPersonByFirstNameAndLastName(String firstName , String lastName) {
		log.debug("Search for the person by first and last name");
		for (Person person : persons) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				log.debug(FOUND);
				return person;
			}
		}
		log.debug(NOTFOUND);
		return null;
	}
}