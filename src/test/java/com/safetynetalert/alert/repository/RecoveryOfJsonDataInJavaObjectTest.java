package com.safetyNetAlert.Alert.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.util.ResourceUtils;

import com.jsoniter.JsonIterator;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.DescriptionOfUncleanJavaObjects;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;

@WebMvcTest(RecoveryOfJsonDataInJavaObjectTest.class)
class RecoveryOfJsonDataInJavaObjectTest {

	private final static Logger log = LogManager.getLogger(); 

	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
		repository.initialize();
		Assert.assertEquals(23, repository.getPersons().size());
		Assert.assertEquals(13,repository.getFirestations().size());
		Assert.assertEquals(23,repository.getMedicalrecords().size());
	}

	private Person getPersonByFirstNameAndLastName(String firstName, String lastName)
	{
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		for(Person person : persons)
		{
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
			{
				return person;
			}
		}		
		return null;
	}

	/**
	 * Test to verify initialisation of the list
	 */
	@Test
	void initialiseTest() {

		try {
			// initialise loads the JSON file into memory
			RecoveryOfJsonDataInJavaObject.getInstance().initialize();

			// verification of the loading in memory of the JSON file
			File file = ResourceUtils.getFile("classpath:data.json");
			String content = Files.readString(file.toPath());
			DescriptionOfUncleanJavaObjects jsonDataInJavaObject = JsonIterator.deserialize(content, DescriptionOfUncleanJavaObjects.class);

			//Comparison of the JSON file in memory with the entity repository
			List<PersonsEntity> personEntities = jsonDataInJavaObject.getPersons();
			for(PersonsEntity personEntity : personEntities)
			{
				Person person = getPersonByFirstNameAndLastName(personEntity.getFirstName(), personEntity.getLastName());
				Assert.assertNotNull(person);
				Assert.assertEquals(person.getFirstName(),personEntity.getFirstName());
				Assert.assertEquals(person.getLastName(),personEntity.getLastName());
				Assert.assertEquals(person.getAddress().getStreet(),personEntity.getAddress());
				Assert.assertEquals(person.getAddress().getCity(),personEntity.getCity());
				Assert.assertEquals(person.getAddress().getZip(),personEntity.getZip());
				Assert.assertEquals(person.getPhone(),personEntity.getPhone());
				Assert.assertEquals(person.getEmail(),personEntity.getEmail());
			}

			List<MedicalRecordsEntity> medicalRecordEntities = jsonDataInJavaObject.getMedicalrecords();
			for(MedicalRecordsEntity medicalRecordEntity : medicalRecordEntities)
			{
				Person person = getPersonByFirstNameAndLastName(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName());
				Assert.assertEquals(person.getFirstName(),medicalRecordEntity.getFirstName());
				Assert.assertEquals(person.getLastName(),medicalRecordEntity.getLastName());
				Assert.assertEquals(person.getBirthDate(),medicalRecordEntity.getBirthdate());
				Assert.assertTrue(person.getMedicalRecord().getMedication().containsAll(medicalRecordEntity.getMedications()));
				Assert.assertTrue(person.getMedicalRecord().getAllergies().containsAll(medicalRecordEntity.getAllergies()));
			}

			List<FirestationsEntity> firestationEntities = jsonDataInJavaObject.getFirestations();
			List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
			for(FirestationsEntity firestationEntity : firestationEntities)
			{
				for (Person person : persons) {
					Integer firestationIdOfEntity = Integer.parseInt(firestationEntity.getStation());
					if(person.getAddress().getStreet().compareTo(firestationEntity.getAddress())==0)
					{
						Assert.assertTrue(person.getAddress().getFireStationIds().contains(firestationIdOfEntity));
					}
				}
			}
		} catch (IOException e) {
			log.error("IO Exception  : ", e.getMessage());
			assert(false);
		}
	}
}
