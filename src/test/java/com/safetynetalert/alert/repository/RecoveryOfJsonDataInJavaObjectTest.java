package com.safetynetalert.alert.repository;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;

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
	}
	/**
	 * clean the list after each Test
	 */
	@AfterEach
	public void tearDown(){
		List<Person> result = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		result.clear();
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
	/**
	 * Test to verify initialization of the list
	 */
	@Test
	public void initializeTest() {
		//TODO Methode à compléter
	}

	//--------------------------------------Person----------------------------------------------------
	
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
		//TODO methode à compléter
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
	
	/**
	 * Test to verify an addition of a mapping of a firestation
	 */
	@Test
	public void addMappingTest() {
		//TODO methode à compléter
	}
	
	/**
	 * Test to verify the modification of a mapping of a firestation for a specific address 
	 */
	@Test
	public void updateMappingTest() {
		//TODO methode à compléter
	}
	
	/**
	 * Test to verify the deletion of a mapping of a firestation 
	 */
	@Test
	public void deleteMappingOfAFirestationTest() {
		//TODO methode à compléter
	}
	
	/**
	 * Test to verify the deletion of a mapping of a specific address
	 */
	@Test
	public void deleteMappingOfAnAddressTest() {
		//TODO methode à compléter
	}
	
	//---------------------------------------MedicalRecord--------------------------------------------
	
	/**
	 * Test to verify an addition of a medical record of a specific person
	 */
	@Test
	public void addMedicalRecordForASpecificPersonTest() {
		//TODO methode à compléter
	}
	
	/**
	 * Test to verify the modification of the medical record for a specific person 
	 */
	@Test
	public void updateMedicalRecordForASpecificPersonTest() {
		//TODO methode à compléter
	}
	
	/**
	 * Test to verify the deletion of the medical record for a specific person
	 */
	@Test
	public void deleteMedicalRecordForASpecificPersonTest() {
		//TODO methode à compléter
	}
}
