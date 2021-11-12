package com.safetyNetAlert.Alert.repository;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.repository.RepositoryFirestation;

@WebMvcTest(RepositoryFirestationTest.class)
class RepositoryFirestationTest {
	

	private RepositoryFirestation repositoryFirestation = new RepositoryFirestation();

	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
	}

	/**
	 * Test to verify an addition of a mapping of a fireStation
	 */
	@Test
	void addMappingTest() {

		//GIVEN
		int firestationId = 5;
		String address = "1509 Culver St";

		//WHEN
		repositoryFirestation.addMapping(firestationId,address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertTrue(persons.get(0).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(0).getAddress().getStreet(), address);
		Assert.assertTrue(persons.get(1).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(1).getAddress().getStreet(),address);
		Assert.assertTrue(persons.get(3).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(3).getAddress().getStreet(),address);
	}


	/**
	 * Test to verify the modification of a mapping of a fireStation for a specific address 
	 */
	@Test
	void updateMappingTest() {

		//GIVEN
		int oldFirestationId = 3;
		int newFirestationId = 5;
		String address = "1509 Culver St";

		//WHEN
		repositoryFirestation.updateFirestationNumber(oldFirestationId, newFirestationId, address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertTrue(persons.get(0).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(0).getAddress().getStreet(),address);
		Assert.assertTrue(persons.get(1).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(1).getAddress().getStreet(),address);
		Assert.assertTrue(persons.get(3).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(3).getAddress().getStreet(),address);
	}


	/**
	 * Test to verify the deletion of a mapping of a fireStation 
	 */
	@Test
	void deleteMappingOfAFirestationTest() {

		//GIVEN
		int firestationId = 3;

		//WHEN
		repositoryFirestation.deleteMappingByFirestationId(firestationId);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertTrue(persons.get(0).getAddress().getFireStationIds().isEmpty());
		Assert.assertTrue(persons.get(6).getAddress().getFireStationIds().isEmpty());
		Assert.assertTrue(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

	/**
	 * Test to verify the deletion of a mapping of a specific address
	 */
	@Test
	void deleteMappingOfAnAddressTest() {

		//GIVEN
		String address = "748 Townings Dr";

		//WHEN
		repositoryFirestation.deleteMappingByAddress(address);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertTrue(persons.get(8).getAddress().getFireStationIds().isEmpty());
		Assert.assertTrue(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

}
