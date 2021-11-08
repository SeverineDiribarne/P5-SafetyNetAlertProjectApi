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
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

public class RecoveryOfJsonDataInJavaObject {

	private static final Logger log = LogManager.getLogger(); 
	private static final String found = "person found";
	private static final String notFound = "person not found";

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
	 * Fill model with Json Data Object
	 * @param personsEntities
	 * @param fireStationsEntities
	 * @param medicalrecordsEntities
	 */
	private void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
		log.debug("Start of loading the json file into memory in the model");
		//fireStationsEntities
		for (FirestationsEntity fireStationEntity : fireStationsEntities) {
			FireStation fireStation = new FireStation();
			fireStation.setStationId(Integer.parseInt(fireStationEntity.getStation()));
			this.firestations.add(fireStation);
			log.debug(" . Add fireStation {} into model", fireStation);
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
					address.addFirestationId(Integer.valueOf(Integer.parseInt(fireStationEntity.getStation()))); 
				}
			} 
			person.setId(id++);
			persons.add(person);
			log.debug(" . Add person {} into model", person );
		} 

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
	 * Get person by firstname and lastname
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	private Person getPersonByFirstNameAndLastName(String firstName , String lastName) {
		log.debug("Search for the person by first and last name");
		for (Person person : persons) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				log.debug(found);
				return person;
			}
		}
		log.debug(notFound);
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
				log.debug(found);
				personsFound.add(person);
				return personsFound;
			}
		}
		log.debug(notFound);
		return Collections.emptyList();

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
				log.debug(found);
				personsFound.add(person);
				return personsFound;
			}
		}
		log.debug(notFound);
		return Collections.emptyList();
	}

	//--------------------------------------------/person---------------------------------------------------------------

	/**
	 * Add a new person 
	 * @param person
	 * @return person
	 */
	public boolean addPerson(Person person) {
		if(person != null) {
		 persons.add(person);
		 log.debug(" Return true : the person found is added of the list of persons");
		 return true;
		}
		log.debug("Return false : the person found isn't added of the list of persons");
		return false;
	}

	/**
	 * update Address
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
	 * @param person
	 * @return person
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
	 */
	public boolean deletePerson(String firstName, String lastName) {
		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);
		if(personFound != null) {
			log.debug("Return true : the person found is deleted of the list of persons");
			persons.remove(personFound);
			return true;
		}
		log.debug("Return false : the person found isn't deleted of the list of persons");
		return false;
	}

	//-----------------------------------------/firestation-------------------------------------------------------------

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	public boolean addMapping(Integer firestationId, String address) {

		List<Person> personsFound = getPersonByAddress(address);
		if(personsFound != null) {
			for(Person person : personsFound) {
				person.getAddress().addFirestationId(firestationId);
			}
			log.debug("Return true : the mapping is added for each person found");
			return true;
		}
		log.debug("Return false : the mapping isn't added for each person found");
		return false;
	}

	/**
	 * Update the Firestation Number based on an address
	 * @param firestationId
	 * @param address
	 */
	public boolean updateFirestationNumber(Integer oldfirestationId, Integer newfirestationId, String address) {
		List<Person> personsFound = getPersonByAddress(address);
		if(personsFound != null) {
			for(Person person : personsFound) {
				person.getAddress().updateFirestationId(oldfirestationId, newfirestationId);
			}
			log.debug("Return true : the firestationId is updated for each person found");
			return true;
		}
		log.debug("Return false : the firestationId isn't updated for each person found");
		return false;
	}

	/**
	 * Delete mapping by FirestationId
	 * @param firestationId
	 * @param address
	 */
	public boolean deleteMappingByFirestationId(Integer firestationId) {
		//Delete the mapping by fireStationId
		if(firestationId != null) {
			List<Person> personsFound = getPersonByFirestationId(firestationId);
			for(Person person : personsFound) {
				person.getAddress().removeFirestationId(firestationId);
			}
			log.debug("Return true : the mapping is cleared for each person found");
			return true;
		}
		log.debug("Return false : the mapping isn't cleared for each person found");
		return false;
	}

	/**
	 * Delete the mapping by address
	 * @param address
	 */
	public boolean deleteMappingByAddress(String address) {
		//Delete the mapping by address
		if(address != null) {
			List<Person> personsFound=getPersonByAddress(address);
			for(Person person : personsFound) {
				person.getAddress().removeAllFirestationIds();
			}
			log.debug("Return true : the mapping is cleared for each person found");
			return true;
		}
		log.debug("Return false : the mapping isn't cleared for each person found");
		return false;	
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
				log.debug(found);
				return person;
			}
		}
		log.debug(notFound);
		return null;
	}

	/**
	 * Add a medical record
	 * @param personId
	 * @param medicalRecord
	 * @return 
	 */
	public boolean addMedicalRecord(int personId,MedicalRecord medicalRecord ) {
		Person personFound = getPersonById(personId);
		if(personFound !=null) {
			log.debug("Return true : the medical record has been added to the person found");
			personFound.setMedicalRecord(medicalRecord);
			return true;
		}
		log.debug(" return false : the medical record hasn't been added to the person found");
		return false;
	}

	/**
	 * Update medical record of a specific person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	public boolean updateMedicalRecord(String firstName, String lastName, Person person) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			log.debug("The medical record has been updated to the person found");
			personFound.setMedicalRecord(person.getMedicalRecord());
			return true;
		}
		log.debug("The medical record hasn't been updated to the person found");
		return false;	
	}

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	public boolean deleteMedicalRecord(String firstName, String lastName) {

		Person personFound = getPersonByFirstNameAndLastName(firstName, lastName);

		if(personFound != null) {
			log.debug("The medical record has been deleted to the person found");
			personFound.getMedicalRecord().setMedication(null);
			personFound.getMedicalRecord().setAllergies(null);
			return true;
		}
		log.debug("The medical record hasn't been deleted to the person found");
		return false;
	}
}