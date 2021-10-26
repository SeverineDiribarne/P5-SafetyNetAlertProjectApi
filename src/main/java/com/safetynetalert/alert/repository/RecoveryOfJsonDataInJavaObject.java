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
<<<<<<< Updated upstream
=======

import org.apache.commons.collections.CollectionUtils;
>>>>>>> Stashed changes
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
		
		this.persons.clear();
		this.firestations.clear();
		this.medicalrecords.clear();
		
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
<<<<<<< Updated upstream
	public void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
=======
	private void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {

		//fireStationsEntities
>>>>>>> Stashed changes
		for (FirestationsEntity fireStationEntity : fireStationsEntities) {
			FireStation fireStation = new FireStation();
			fireStation.setStationId(Integer.parseInt(fireStationEntity.getStation()));
			this.firestations.add(fireStation);
		} 
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
					address.getFireStationIds().add(Integer.valueOf(Integer.parseInt(fireStationEntity.getStation()))); 
				}
			} 
			person.setId(id++);
			persons.add(person);
		} 

		//medicalrecordsEntities
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
<<<<<<< Updated upstream
=======

	/**
	 * Get person by address
	 * @param address
	 * @return
	 */
	private List<Person> getPersonByAddress(String address) {
		List<Person> personsFound = new ArrayList<>();
		for (Person person : persons) {
			if (person.getAddress().getStreet().equals(address)) {
				personsFound.add(person);
			}
		}
		return personsFound;
	}

	/**
	 * get person by firestationId
	 * @param firestationId
	 * @return
	 */
	private List<Person> getPersonByFirestationId(Integer firestationId) {
		List<Person> personsFound = new ArrayList<>();
		for (Person person : persons) {
			if(person.getAddress().containsFirestationId(firestationId)) {
				personsFound.add(person);
			}
		}
		return personsFound;
	}

>>>>>>> Stashed changes
	//--------------------------------------------/person---------------------------------------------------------------
	
	/**
	 * Add a new person 
	 * @param person
	 * @return person
	 */
	public void addPerson(Person person) {
		persons.add(person);
<<<<<<< Updated upstream
		return person;

	}
=======
	}

>>>>>>> Stashed changes
	/**
	 * Update a person
	 * @param person
	 * @return person
	 */
	public void updatePerson(String firstName, String lastName, Person person) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {

<<<<<<< Updated upstream
			personFound.setAddress(person.getAddress());
			personFound.setBirthDate(person.getBirthDate());
			personFound.setAge(person.getAge());
			personFound.setPhone(person.getPhone());
			personFound.setEmail(person.getEmail());
			personFound.setId(person.getId());
			personFound.setMedicalRecord(person.getMedicalRecord());
		}
	}
=======
			if(person.getAddress()!=null) {
				if((person.getAddress().getStreet()!= null) && !(person.getAddress().getStreet().isEmpty())) {
					personFound.getAddress().setStreet(person.getAddress().getStreet());
				}
				if((person.getAddress().getCity()!= null) && !(person.getAddress().getCity().isEmpty())) {
					personFound.getAddress().setCity(person.getAddress().getCity());
				}
				if((person.getAddress().getZip()!= null) && !(person.getAddress().getZip().isEmpty())) {
					personFound.getAddress().setZip(person.getAddress().getZip());
				}
				if((person.getAddress().getFireStationIds()!=null)&& CollectionUtils.isNotEmpty(person.getAddress().getFireStationIds())) {
					personFound.getAddress().setFireStationIds(person.getAddress().getFireStationIds());
				}
			}
			if((person.getBirthDate()!= null) && !(person.getBirthDate().isEmpty())) {
				personFound.setBirthDate(person.getBirthDate());	
			}
			if((person.getEmail()!=null) && !(person.getEmail().isEmpty())) {
				personFound.setEmail(person.getEmail());
			}
			if(person.getMedicalRecord()!=null) {
				if((person.getMedicalRecord().getMedication() != null ) && CollectionUtils.isNotEmpty(person.getMedicalRecord().getMedication())) {
					personFound.getMedicalRecord().setMedication(person.getMedicalRecord().getMedication());
				}
				if((person.getMedicalRecord().getAllergies() != null) && CollectionUtils.isNotEmpty(person.getMedicalRecord().getAllergies())) {
					personFound.getMedicalRecord().setAllergies(person.getMedicalRecord().getAllergies());
				}
			}
			if((person.getPhone()!= null) && !(person.getPhone().isEmpty())) {
				personFound.setPhone(person.getPhone());
			}
		}
	}

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
	
	//----------------------------------------/medicalRecord------------------------------------------------------------
	
=======

>>>>>>> Stashed changes
	/**
	 * Add a medical record
	 * @param medicalRecord
	 * @return medicalRecord
	 */
<<<<<<< Updated upstream
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		medicalrecords.add(medicalRecord);
		return medicalRecord;
=======
	public void deleteMappingByAddress(String address) {
		//Delete the mapping by address
		if(address != null) {
			List<Person> personsFound=getPersonByAddress(address);
			for(Person person : personsFound) {
				person.getAddress().removeAllFirestationIds();
			}
		}	
	}

	//----------------------------------------/medicalRecord------------------------------------------------------------

	/**
	 * Get person by Id
	 * @param personId
	 * @return
	 */
	private Person getPersonById(int personId) {
		for (Person person : persons) {
			if (person.getId()==(personId)) {
				return person;
			}
		}
		return null;
	}

	/**
	 * Add a medical record
	 * @param personId
	 * @param medicalRecord
	 */
	public void addMedicalRecord(int personId,MedicalRecord medicalRecord ) {
		Person personFound = getPersonById(personId);
		if(personFound !=null) {
			personFound.setMedicalRecord(medicalRecord);
		}
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
		
		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			personFound.setMedicalRecord(null);
=======

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			personFound.getMedicalRecord().setMedication(null);
			personFound.getMedicalRecord().setAllergies(null);
>>>>>>> Stashed changes
		}
	}
}