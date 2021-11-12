package com.safetyNetAlert.Alert.SafetyAlertsDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsDAO;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;


@WebMvcTest(SafetyAlertsDAOTest.class)
class SafetyAlertsDAOTest {

	SafetyAlertsDAO safetyAlertDao = new SafetyAlertsInMemoryDAO();

	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
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
	 * Test to verify the existence of people in the list
	 */
	@Test
	void getPersonsTest() {

		List<Person>result = safetyAlertDao.getPersons();
		Assert.assertEquals( "John",result.get(0).getFirstName());	
		Assert.assertEquals( "Boyd",result.get(0).getLastName());
		Assert.assertEquals( "1509 Culver St",result.get(0).getAddress().getStreet());
		Assert.assertEquals( "Culver",result.get(0).getAddress().getCity());
		Assert.assertEquals( "97451",result.get(0).getAddress().getZip());
		Assert.assertEquals( "03/06/1984",result.get(0).getBirthDate());
		Assert.assertEquals( "jaboyd@email.com",result.get(0).getEmail());
		Assert.assertEquals( "841-874-6512",result.get(0).getPhone());
	}
	/**
	 * Test to verify the existence of fireStations in the list
	 */
	@Test
	void getFirestationsTest() {
		List<FireStation>result = safetyAlertDao.getFireStations();
		Assert.assertEquals( 3,result.get(0).getStationId());
	}

	/**
	 * Test to verify the existence of medical records in the list
	 */
	@Test
	void getMedicalRecordsTest() {
		List<MedicalRecord> result = safetyAlertDao.getMedicalRecords();
		Assert.assertEquals( Arrays.asList("aznol:350mg","hydrapermazol:100mg"),result.get(0).getMedication());
		Assert.assertEquals(Arrays.asList("nillacilan"),result.get(0).getAllergies());
	}
}