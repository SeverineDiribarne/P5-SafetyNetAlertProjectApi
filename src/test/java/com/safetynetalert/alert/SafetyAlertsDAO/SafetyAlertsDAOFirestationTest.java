package com.safetyNetAlert.Alert.SafetyAlertsDAO;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsDAO;
import com.safetynetalert.alert.service.SafetyAlertsDAOFirestation;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOFirestation;

@WebMvcTest(SafetyAlertsDAOFirestationTest.class)
class SafetyAlertsDAOFirestationTest {

	SafetyAlertsDAOFirestation safetyAlertDaoFirestation = new SafetyAlertsInMemoryDAOFirestation();
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
	 * Test to verify an addition of a mapping of a fireStation
	 */
	@Test
	void addMappingTest() {

		//GIVEN
		int firestationId = 5;
		String address = "1509 Culver St";
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoFirestation.addMapping(firestationId,address);

		//THEN
		Assert.assertTrue(persons.get(0).getAddress().getFireStationIds().contains(5));
		Assert.assertEquals(persons.get(0).getAddress().getStreet(),address);
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
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoFirestation.updateFirestationNumber(oldFirestationId, newFirestationId, address);

		//THEN
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
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoFirestation.deleteMappingByFirestationId(firestationId);

		//THEN
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
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoFirestation.deleteMappingByAddress(address);

		//THEN
		Assert.assertTrue(persons.get(8).getAddress().getFireStationIds().isEmpty());
		Assert.assertTrue(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

}
