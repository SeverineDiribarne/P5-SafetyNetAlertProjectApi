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
import java.util.HashMap;
import java.util.List;
import org.springframework.util.ResourceUtils;

public class RecoveryOfJsonDataInJavaObject {

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
	private HashMap<Integer, String> mappingFirestationIdWithAnAddress = new HashMap<Integer, String>();


	/**
	 * Getter Persons
	 * @return this persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Getter Firestations
	 * @return firestations
	 */
	public List<FireStation> getFirestations() {
		return this.firestations;
	}

	/**
	 * Getter MedicalRecords
	 * @return medicalrecords
	 */
	public List<MedicalRecord> getMedicalrecords() {
		return this.medicalrecords;
	}

	/**
	 * Initialize method : read json et retrieving json info
	 * @throws IOException
	 */
	public void initialize() throws IOException {

		File file = ResourceUtils.getFile("classpath:data.json");
		String content = Files.readString(file.toPath());
		DescriptionOfUncleanJavaObjects jsonDataInJavaObject = JsonIterator.deserialize(content, DescriptionOfUncleanJavaObjects.class);

		fillModelWithJsonDataObjects(jsonDataInJavaObject.getPersons(), jsonDataInJavaObject.getFirestations(), jsonDataInJavaObject.getMedicalrecords());
	}

	/**
	 * Fill model with Json Data Object
	 * @param personsEntities
	 * @param fireStationsEntities
	 * @param medicalrecordsEntities
	 */
	public void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
		for (FirestationsEntity fireStationEntity : fireStationsEntities) {
			FireStation fireStation = new FireStation();
			fireStation.setStationId(Integer.parseInt(fireStationEntity.getStation()));
			this.firestations.add(fireStation);
		} 
		int id = 0;
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
					address.getFireStationIds().add(Integer.valueOf(Integer.parseInt(fireStationEntity.getStation()))); 
				}
			} 
			person.setId(id++);
			persons.add(person);
		} 
		for (MedicalRecordsEntity medicalRecordEntity : medicalrecordsEntities) {
			Person person = getPersonByFirstNameAndLastName(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName());
			if (person != null) {
				person.setBirthDate(medicalRecordEntity.getBirthdate());
				MedicalRecord medicalRecord = new MedicalRecord();
				medicalRecord.setMedication(medicalRecordEntity.getMedications());
				medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
				person.setMedicalRecord(medicalRecord);
			} 
		} 
	}
	/**
	 * Get person by firstname and lastname
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	private Person getPersonByFirstNameAndLastName(String firstName , String lastName) {
		for (Person person : persons) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				return person;
			}
		}
		return null;
	}
	//--------------------------------------------/person---------------------------------------------------------------
	
	/**
	 * Add a new person 
	 * @param person
	 * @return person
	 */
	public Person addPerson(Person person) {
		persons.add(person);
		return person;

	}
	/**
	 * Update a person
	 * @param person
	 * @return person
	 */
	public void updatePerson(String firstName, String lastName, Person person) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {

			personFound.setAddress(person.getAddress());
			personFound.setBirthDate(person.getBirthDate());
			personFound.setAge(person.getAge());
			personFound.setPhone(person.getPhone());
			personFound.setEmail(person.getEmail());
			personFound.setId(person.getId());
			personFound.setMedicalRecord(person.getMedicalRecord());
		}
	}
	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 */
	public void deletePerson(String firstName, String lastName) {
		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);
		if(personFound != null) {
			persons.remove(personFound);
		}
	}
	
    //-----------------------------------------/firestation-------------------------------------------------------------
	
	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	public void addMapping(Integer firestationId, String address) {
		mappingFirestationIdWithAnAddress.put(firestationId,address);
	}
	
	/**
	 * Update the fireStation number based on an address
	 * @param firestationId
	 * @param address
	 */
	public void updateFirestationNumber(Integer firestationId, String address) {
		// TODO Auto-generated method stub
		
	}
	
	public void deleteMapping(Integer firestationId, String address) {
		// TODO Auto-generated method stub
		
	}
	
	//----------------------------------------/medicalRecord------------------------------------------------------------
	
	/**
	 * Add a medical record
	 * @param medicalRecord
	 * @return medicalRecord
	 */
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		medicalrecords.add(medicalRecord);
		return medicalRecord;
	}

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public void updateMedicalRecord(String firstName, String lastName, Person person) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			personFound.setMedicalRecord(person.getMedicalRecord());
		}
	}

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	public void deleteMedicalRecord(String firstName, String lastName) {
		
		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			personFound.setMedicalRecord(null);
		}
	}
}