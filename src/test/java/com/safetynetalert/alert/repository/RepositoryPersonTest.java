package com.safetyNetAlert.Alert.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.repository.RepositoryPerson;

@WebMvcTest(RepositoryPersonTest.class)
class RepositoryPersonTest {

	
	private RepositoryPerson repositoryPerson = new RepositoryPerson();
	
	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
	}
	
	/**
	 * Test to verify an addition of a new person in the list
	 * @throws Exception
	 */
	@Test
	void addPersonTest() throws Exception{

		//adding a new person in the list
		Person personToAdd = DataTestsHelper.creationANewPerson();

		// add this person in the list
		repositoryPerson.addPerson(personToAdd);

		// check the new person in the list
		List<Person> result = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		boolean personFound = false;
		for(Person person : result){
			if(person.getFirstName().contains("Jane")&& person.getLastName().contains("Doe")) {
				personFound = true;	
			}
		}
		Assert.assertEquals(true,personFound);
	}

	/**
	 * Test to verify the modification of information of a person in the list
	 * @throws Exception
	 */
	@Test
	void updatePersonTest()throws Exception{

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
		repositoryPerson.updatePerson(firstName, lastName, personBody);

		//WHEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();

		for(Person person : persons)
		{
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) 
			{
				Assert.assertEquals(person.getAddress().getStreet(),personBody.getAddress().getStreet());
				Assert.assertEquals(person.getAddress().getCity(),personBody.getAddress().getCity());
				Assert.assertEquals(person.getAddress().getZip(),personBody.getAddress().getZip());
				Assert.assertTrue(person.getAddress().getFireStationIds().containsAll(personBody.getAddress().getFireStationIds()));
				Assert.assertEquals(person.getPhone(),personBody.getPhone());
				Assert.assertEquals(person.getEmail(),personBody.getEmail());
				Assert.assertEquals(person.getBirthDate(),personBody.getBirthDate());
				Assert.assertTrue(person.getMedicalRecord().getAllergies().containsAll(personBody.getMedicalRecord().getAllergies()));
				Assert.assertTrue(person.getMedicalRecord().getMedication().containsAll(personBody.getMedicalRecord().getMedication()));
			}	
		}
	}
	/**
	 * Test to verify the modification of information of a person in the list
	 * @throws Exception
	 */
	@Test
	void updateBadPersonTest()throws Exception{

		//GIVEN
		String firstName = "Jane";
		String lastName = "Doe";
		Person personBody = new Person();

		Address addressBody = new Address();
		addressBody.setStreet("19 Altavista St"); 
		addressBody.setCity("Bordeaux");
		addressBody.setZip("33000");
		addressBody.setFireStationIds(Arrays.asList(6, 7));
		personBody.setAddress(addressBody);
		personBody.setPhone("492-642-8952");
		personBody.setEmail("jane.doe@gmail.com");
		personBody.setBirthDate("12/10/1992");
		MedicalRecord medicalRecordBody = new MedicalRecord();
		medicalRecordBody.setMedication(Arrays.asList("paracetamol:1000mg"));
		medicalRecordBody.setAllergies(Arrays.asList("Lactose"));
		personBody.setMedicalRecord(medicalRecordBody);

		//THEN
		List<Person> personsBeforeTest = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();

		repositoryPerson.updatePerson(firstName, lastName, personBody);

		//WHEN
		List<Person> personsAfterTest = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertEquals(personsBeforeTest, personsAfterTest);
			
	}
	/**
	 * Test to verify the modification of information of a person in the list
	 * @throws Exception
	 */
	@Test
	void updatePersonRobustnessTest()throws Exception{

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
		repositoryPerson.updatePerson(firstName, lastName, personBody);
		repositoryPerson.updatePerson(firstName2, lastName2, personBody2);


		//WHEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();

		for (Person person : persons) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				Assert.assertEquals("1509 Culver St",person.getAddress().getStreet());
				Assert.assertEquals("Culver",person.getAddress().getCity());
				Assert.assertEquals("97451",person.getAddress().getZip());
				Assert.assertTrue(person.getAddress().getFireStationIds().containsAll(Arrays.asList(3)));
				Assert.assertEquals("841-874-6512",person.getPhone());
				Assert.assertEquals("jaboyd@email.com",person.getEmail());
				Assert.assertEquals("03/06/1984",person.getBirthDate());
				Assert.assertTrue(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")));
				Assert.assertTrue(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("nillacilan")));
				
			}
			if (person.getFirstName().equals(firstName2) && person.getLastName().equals(lastName2)) {
				Assert.assertEquals("947 E. Rose Dr",person.getAddress().getStreet());
				Assert.assertEquals("Culver",person.getAddress().getCity());
				Assert.assertEquals("97451",person.getAddress().getZip());
				Assert.assertTrue(person.getAddress().getFireStationIds().containsAll(Arrays.asList(1)));
				Assert.assertEquals("841-874-7784",person.getPhone());
				Assert.assertEquals("bstel@email.com",person.getEmail());
				Assert.assertEquals("12/06/1975",person.getBirthDate());
				Assert.assertTrue(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("nillacilan")));
				Assert.assertTrue(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg")));
			}
		}
	}

	/**
	 * Test to verify the deletion of a person from the list	 
	 * @throws Exception
	 */
	@Test
	void deletePersonTest()throws Exception{

		repositoryPerson.deletePerson("John", "Boyd");

		List<Person> resultList = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		boolean personFound = false;

		for(Person person : resultList) {
			if (person.getFirstName().contains("John") && person.getLastName().contains("Boyd")) {
				personFound = true;
			}
		}
		Assert.assertFalse(personFound);
	}

}
