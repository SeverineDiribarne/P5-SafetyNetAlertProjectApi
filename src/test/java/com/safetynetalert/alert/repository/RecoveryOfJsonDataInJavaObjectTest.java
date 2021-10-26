package com.safetynetalert.alert.repository;

<<<<<<< HEAD

import java.io.IOException;
=======
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
>>>>>>> feature/Tests
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
<<<<<<< HEAD

import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
=======
import org.springframework.util.ResourceUtils;

import com.jsoniter.JsonIterator;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;
>>>>>>> feature/Tests

@WebMvcTest(RecoveryOfJsonDataInJavaObjectTest.class)
public class RecoveryOfJsonDataInJavaObjectTest {

	/**
	 * Initialize the json list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
		repository.initialize();
<<<<<<< HEAD
	}
	/**
	 * clean the list after each Test
	 */
	@AfterEach
	public void tearDown(){
		List<Person> result = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		result.clear();
=======
		assert(repository.getPersons().size() == 23);
		assert(repository.getFirestations().size() == 13);
		assert(repository.getMedicalrecords().size() == 23);
>>>>>>> feature/Tests
	}

	/**
	 * Create a new person for test
	 * @return Person to add
	 */
	public Person creationANewPerson() {

		// creation new person to add
		Person personToAdd = new Person();
		personToAdd.setId(23);
		personToAdd.setFirstName("Jane");
		personToAdd.setLastName("Doe");
		personToAdd.setAddress(new Address());
		personToAdd.getAddress().setStreet("290 MullowLand Dr"); 
		personToAdd.getAddress().setCity("Culver");
		personToAdd.getAddress().setZip("33000");
		personToAdd.setBirthDate("10/29/1980");
		personToAdd.setEmail("janedoe@gmail.com");
		personToAdd.setPhone("480-461-7789");
		personToAdd.setMedicalRecord(new MedicalRecord());
		personToAdd.getMedicalRecord().setMedication(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg"));
		personToAdd.getMedicalRecord().setAllergies(Arrays.asList("seafood" , "gluten"));

		return personToAdd;	
	}
<<<<<<< HEAD
=======

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

>>>>>>> feature/Tests
	/**
	 * Test to verify initialization of the list
	 */
	@Test
	public void initializeTest() {
<<<<<<< HEAD
		//TODO Methode à compléter
	}

	//--------------------------------------Person----------------------------------------------------
	
=======

		try {
			// initialize loads the json file into memory
			RecoveryOfJsonDataInJavaObject.getInstance().initialize();

			// verification of the loading in memory of the json file
			File file = ResourceUtils.getFile("classpath:data.json");
			String content = Files.readString(file.toPath());
			DescriptionOfUncleanJavaObjects jsonDataInJavaObject = JsonIterator.deserialize(content, DescriptionOfUncleanJavaObjects.class);

			//Comparison of the json file in memory with the entity repository
			List<PersonsEntity> personEntities = jsonDataInJavaObject.getPersons();
			for(PersonsEntity personEntity : personEntities)
			{
				Person person = getPersonByFirstNameAndLastName(personEntity.getFirstName(), personEntity.getLastName());
				assert(person != null);
				assert(person.getFirstName().compareTo(personEntity.getFirstName())==0);
				assert(person.getLastName().compareTo(personEntity.getLastName())==0);
				assert(person.getAddress().getStreet().compareTo(personEntity.getAddress()) == 0);
				assert(person.getAddress().getCity().compareTo(personEntity.getCity()) == 0);
				assert(person.getAddress().getZip().compareTo(personEntity.getZip()) == 0);
				assert(person.getPhone().equals(personEntity.getPhone()));
				assert(person.getEmail().compareTo(personEntity.getEmail()) == 0);
			}

			List<MedicalRecordsEntity> medicalRecordEntities = jsonDataInJavaObject.getMedicalrecords();
			for(MedicalRecordsEntity medicalRecordEntity : medicalRecordEntities)
			{
				Person person = getPersonByFirstNameAndLastName(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName());
				assert(person.getFirstName().compareTo(medicalRecordEntity.getFirstName())==0);
				assert(person.getLastName().compareTo(medicalRecordEntity.getLastName())==0);
				assert(person.getBirthDate().compareTo(medicalRecordEntity.getBirthdate())==0);
				assert(person.getMedicalRecord().getMedication().containsAll(medicalRecordEntity.getMedications()));
				assert(person.getMedicalRecord().getAllergies().containsAll(medicalRecordEntity.getAllergies()));
			}

			List<FirestationsEntity> firestationEntities = jsonDataInJavaObject.getFirestations();
			List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
			for(FirestationsEntity firestationEntity : firestationEntities)
			{
				for (Person person : persons) {
					Integer firestationIdOfEntity = Integer.parseInt(firestationEntity.getStation());
					if(person.getAddress().getStreet().compareTo(firestationEntity.getAddress())==0)
					{
						assert(person.getAddress().getFireStationIds().contains(firestationIdOfEntity));
					}
				}
			}
		} catch (IOException e) {
			assert(false);
		}
	}

	//--------------------------------------Person----------------------------------------------------

>>>>>>> feature/Tests
	/**
	 * Test to verify an addition of a new person in the list
	 * @throws Exception
	 */
	@Test
	public void addPersonTest() throws Exception{

		//adding a new person in the list
		Person personToAdd = creationANewPerson();

		// add this person in the list
		RecoveryOfJsonDataInJavaObject.getInstance().addPerson(personToAdd);

		// check the new person in the list
		List<Person> result = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		boolean personFound = false;
		for(Person person : result){
			if(person.getFirstName().contains("Jane")&& person.getLastName().contains("Doe")) {
				personFound = true;	
			}
		}
		assert(personFound==true);
	}

	/**
	 * Test to verify the modification of information of a person in the list
	 * @throws Exception
	 */
	@Test
	public void updatePersonTest()throws Exception{
<<<<<<< HEAD
		//TODO methode à compléter
=======

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";
		Person personBody = new Person();

		Address addressBody = new Address();
		addressBody.setStreet("290 MullowLand Dr"); 
		addressBody.setCity("Culver");
		addressBody.setZip("33000");
		addressBody.setFireStationIds(Arrays.asList(1, 5));
		personBody.setAddress(addressBody);
		personBody.setPhone("480-461-7789");
		personBody.setEmail("john.boyd@gmail.com");
		personBody.setBirthDate("03/06/1984");
		MedicalRecord medicalRecordBody = new MedicalRecord();
		medicalRecordBody.setMedication(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg"));
		medicalRecordBody.setAllergies(Arrays.asList("Seafood" , "Gluten"));
		personBody.setMedicalRecord(medicalRecordBody);

		//THEN
		RecoveryOfJsonDataInJavaObject.getInstance().updatePerson(firstName, lastName, personBody);

		//WHEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();

		for(Person person : persons)
		{
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) 
			{
				assert(person.getAddress().getStreet().compareTo(personBody.getAddress().getStreet())==0);
				assert(person.getAddress().getCity().compareTo(personBody.getAddress().getCity())==0);
				assert(person.getAddress().getZip().compareTo(personBody.getAddress().getZip())==0);
				assert(person.getAddress().getFireStationIds().containsAll(personBody.getAddress().getFireStationIds()));
				assert(person.getPhone().compareTo(personBody.getPhone())==0);
				assert(person.getEmail().compareTo(personBody.getEmail()) == 0);
				assert(person.getBirthDate().compareTo(personBody.getBirthDate()) == 0);
				assert(person.getMedicalRecord().getAllergies().containsAll(personBody.getMedicalRecord().getAllergies()));
				assert(person.getMedicalRecord().getMedication().containsAll(personBody.getMedicalRecord().getMedication()));
			}	
		}
	}

	/**
	 * Test to verify the modification of information of a person in the list
	 * @throws Exception
	 */
	@Test
	public void updatePersonRobustnessTest()throws Exception{
		// TODO : A debugger

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";
		Person personBody = new Person();

		Address addressBody = new Address();
		addressBody.setStreet(null); 
		addressBody.setCity(null);
		addressBody.setZip(null);
		addressBody.setFireStationIds(null);
		personBody.setAddress(addressBody);
		personBody.setPhone(null);
		personBody.setEmail(null);
		personBody.setBirthDate(null);
		MedicalRecord medicalRecordBody = new MedicalRecord();
		medicalRecordBody.setMedication(null);
		medicalRecordBody.setAllergies(null);
		personBody.setMedicalRecord(medicalRecordBody);


		String firstName2 = "Brian";
		String lastName2 = "Stelzer";
		Person personBody2 = new Person();

		personBody2.setAddress(null); 
		personBody2.setBirthDate(null);
		personBody2.setPhone(null);
		personBody2.setMedicalRecord(null);
		personBody2.setEmail(null);

		//THEN
		RecoveryOfJsonDataInJavaObject.getInstance().updatePerson(firstName, lastName, personBody);
		RecoveryOfJsonDataInJavaObject.getInstance().updatePerson(firstName2, lastName2, personBody2);


		//WHEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();

		for (Person person : persons) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				assert(person.getAddress().getStreet().compareTo("1509 Culver St") == 0);
				assert(person.getAddress().getCity().compareTo("Culver") == 0);
				assert(person.getAddress().getZip().compareTo("97451") == 0);
				assert(person.getAddress().getFireStationIds().containsAll(Arrays.asList(3)));
				assert(person.getPhone().compareTo("841-874-6512") == 0);
				assert(person.getEmail().compareTo("jaboyd@email.com") == 0);
				assert(person.getBirthDate().compareTo("03/06/1984") == 0);
				assert(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")));
				assert(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("nillacilan")));
				
			}
			if (person.getFirstName().equals(firstName2) && person.getLastName().equals(lastName2)) {
				assert(person.getAddress().getStreet().compareTo("947 E. Rose Dr") == 0);
				assert(person.getAddress().getCity().compareTo("Culver") == 0);
				assert(person.getAddress().getZip().compareTo("97451") == 0);
				assert(person.getAddress().getFireStationIds().containsAll(Arrays.asList(1)));
				assert(person.getPhone().compareTo("841-874-7784") == 0);
				assert(person.getEmail().compareTo("bstel@email.com") == 0);
				assert(person.getBirthDate().compareTo("12/06/1975") == 0);
				assert(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("nillacilan")));
				assert(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg")));
			}
		}
>>>>>>> feature/Tests
	}

	/**
	 * Test to verify the deletion of a person from the list	 
	 * @throws Exception
	 */
	@Test
	public void deletePersonTest()throws Exception{

		RecoveryOfJsonDataInJavaObject.getInstance().deletePerson("John", "Boyd");

		List<Person> resultList = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		boolean personFound = false;

		for(Person person : resultList) {
			if (person.getFirstName().contains("John") && person.getLastName().contains("Boyd")) {
				personFound = true;
			}
		}
		assert(personFound==false);
	}

	//---------------------------------------Firestation----------------------------------------------
<<<<<<< HEAD
	
=======

>>>>>>> feature/Tests
	/**
	 * Test to verify an addition of a mapping of a firestation
	 */
	@Test
	public void addMappingTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
=======

		//GIVEN
		int firestationId = 5;
		String address = "1509 Culver St";

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().addMapping(firestationId,address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(0).getAddress().getFireStationIds().contains(5));
		assert(persons.get(0).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(1).getAddress().getFireStationIds().contains(5));
		assert(persons.get(1).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(3).getAddress().getFireStationIds().contains(5));
		assert(persons.get(3).getAddress().getStreet().compareTo(address)==0);
	}


>>>>>>> feature/Tests
	/**
	 * Test to verify the modification of a mapping of a firestation for a specific address 
	 */
	@Test
	public void updateMappingTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
=======

		//GIVEN
		int oldFirestationId = 3;
		int newFirestationId = 5;
		String address = "1509 Culver St";

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().updateFirestationNumber(oldFirestationId, newFirestationId, address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(0).getAddress().getFireStationIds().contains(5));
		assert(persons.get(0).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(1).getAddress().getFireStationIds().contains(5));
		assert(persons.get(1).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(3).getAddress().getFireStationIds().contains(5));
		assert(persons.get(3).getAddress().getStreet().compareTo(address)==0);
	}


>>>>>>> feature/Tests
	/**
	 * Test to verify the deletion of a mapping of a firestation 
	 */
	@Test
	public void deleteMappingOfAFirestationTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
=======

		//GIVEN
		int firestationId = 3;

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByFirestationId(firestationId);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(0).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(6).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

>>>>>>> feature/Tests
	/**
	 * Test to verify the deletion of a mapping of a specific address
	 */
	@Test
	public void deleteMappingOfAnAddressTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
	//---------------------------------------MedicalRecord--------------------------------------------
	
=======

		//GIVEN
		String address = "748 Townings Dr";

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().deleteMappingByAddress(address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(8).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

	//---------------------------------------MedicalRecord--------------------------------------------

>>>>>>> feature/Tests
	/**
	 * Test to verify an addition of a medical record of a specific person
	 */
	@Test
	public void addMedicalRecordForASpecificPersonTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
=======

		//GIVEN
		int personId=10; 
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setMedication(Arrays.asList("Aspirin", "Niflugel"));
		medicalRecord.setAllergies(Arrays.asList("Oyster"));

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().addMedicalRecord(personId, medicalRecord);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(10).getMedicalRecord().getMedication().containsAll(Arrays.asList("Aspirin", "Niflugel")));
		assert(persons.get(10).getMedicalRecord().getAllergies().containsAll(Arrays.asList("Oyster")));
	}

>>>>>>> feature/Tests
	/**
	 * Test to verify the modification of the medical record for a specific person 
	 */
	@Test
	public void updateMedicalRecordForASpecificPersonTest() {
<<<<<<< HEAD
		//TODO methode à compléter
	}
	
=======

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";
		Person personBody = new Person();
		MedicalRecord medicalRecordBody = new MedicalRecord();
		medicalRecordBody.setMedication(Arrays.asList("Niflugel", "Paracetamol"));
		medicalRecordBody.setAllergies(Arrays.asList("Oyster"));
		personBody.setMedicalRecord(medicalRecordBody);

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().updateMedicalRecord(firstName, lastName, personBody);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		for(Person person : persons) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				assert(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("Niflugel", "Paracetamol")) );
				assert(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("Oyster")));
			}
		}
	}

>>>>>>> feature/Tests
	/**
	 * Test to verify the deletion of the medical record for a specific person
	 */
	@Test
	public void deleteMedicalRecordForASpecificPersonTest() {
<<<<<<< HEAD
		//TODO methode à compléter
=======

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";

		//WHEN
		RecoveryOfJsonDataInJavaObject.getInstance().deleteMedicalRecord(firstName, lastName);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		assert(persons.get(9).getMedicalRecord().getMedication()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
		assert(persons.get(9).getMedicalRecord().getAllergies()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
>>>>>>> feature/Tests
	}
}
