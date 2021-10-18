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


	/**
	 * Getter Persons
	 * @return this persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Getter firestations
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
	private void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
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
					address.addFirestationId(Integer.valueOf(Integer.parseInt(fireStationEntity.getStation()))); 
				}
			} 
			person.setId(id++);
			persons.add(person);
		} 
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

			if(!(person.getAddress().getStreet().isEmpty())) {
				personFound.getAddress().setStreet(person.getAddress().getStreet());
			}
			if(!(person.getBirthDate().isEmpty())) {
				personFound.setBirthDate(person.getBirthDate());	
			}
			if(!(person.getEmail().isEmpty())) {
				personFound.setEmail(person.getEmail());
			}
			if(!(person.getMedicalRecord().getMedication().isEmpty())) {
				personFound.getMedicalRecord().setMedication(person.getMedicalRecord().getMedication());
			}
			if(!(person.getMedicalRecord().getAllergies().isEmpty())) {
				personFound.getMedicalRecord().setAllergies(person.getMedicalRecord().getAllergies());
			}
			if(!(person.getPhone().isEmpty())) {
				personFound.setPhone(person.getPhone());
			}
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

		List<Person> personsFound = getPersonByAddress(address);
		for(Person person : personsFound) {
			person.getAddress().addFirestationId(firestationId);
		}
	}

	/**
	 * Update the Firestation Number based on an address
	 * @param firestationId
	 * @param address
	 */
	public void updateFirestationNumber(Integer oldfirestationId, Integer newfirestationId, String address) {
		List<Person> personsFound = getPersonByAddress(address);
		for(Person person : personsFound) {
			person.getAddress().updateFirestationId(oldfirestationId, newfirestationId);
		}
	}

	/**
	 * Delete mapping by FirestationId
	 * @param firestationId
	 * @param address
	 */
	public void deleteMappingByFirestationId(Integer firestationId) {
		//Delete the mapping by fireStationId
		if(firestationId != null) {
			List<Person> personsFound = getPersonByFirestationId(firestationId);
			for(Person person : personsFound) {
				person.getAddress().removeFirestationId(firestationId);
			}
		}
	}
	
	/**
	 * Delete the mapping by address
	 * @param address
	 */
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
		 * Add a medical record
		 * @param personId
		 * @param medicalRecord
		 */
		public void addMedicalRecord(int personId,MedicalRecord medicalRecord ) {
			Person personFound = getPersonById(personId);
			if(personFound !=null) {
				personFound.setMedicalRecord(medicalRecord);
			}
		}
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