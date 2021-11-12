package com.safetyNetAlert.Alert.SafetyAlertsDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsDAO;
import com.safetynetalert.alert.service.SafetyAlertsDAOMedicalRecord;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOMedicalRecord;

@WebMvcTest(SafetyAlertsDAOMedicalRecordTest.class)
class SafetyAlertsDAOMedicalRecordTest {

	SafetyAlertsDAO safetyAlertDao = new SafetyAlertsInMemoryDAO();
	SafetyAlertsDAOMedicalRecord safetyAlertDaoMedicalRecord = new SafetyAlertsInMemoryDAOMedicalRecord();

	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
	}

	/**
	 * Test to verify an addition of a medical record of a specific person
	 */
	@Test
	void addMedicalRecordForASpecificPersonTest() {

		//GIVEN
		int personId=10; 
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setMedication(Arrays.asList("Aspirin", "Niflugel"));
		medicalRecord.setAllergies(Arrays.asList("Oyster"));
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoMedicalRecord.addMedicalRecord(personId, medicalRecord);

		//THEN
		Assert.assertTrue(persons.get(10).getMedicalRecord().getMedication().containsAll(Arrays.asList("Aspirin", "Niflugel")));
		Assert.assertTrue(persons.get(10).getMedicalRecord().getAllergies().containsAll(Arrays.asList("Oyster")));
	}

	/**
	 * Test to verify the modification of the medical record for a specific person 
	 */
	@Test
	void updateMedicalRecordForASpecificPersonTest() {

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";
		Person personToUpdate = new Person();
		List<Person> persons = safetyAlertDao.getPersons();

		for ( Person person : persons) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				personToUpdate = person;
				personToUpdate.getMedicalRecord().setMedication(Arrays.asList("Niflugel", "Paracetamol"));
				personToUpdate.getMedicalRecord().setAllergies(Arrays.asList("Shellfish"));
			}
		}

		//WHEN
		safetyAlertDaoMedicalRecord.updateMedicalRecord(firstName, lastName, personToUpdate );

		//THEN
		Assert.assertEquals(Arrays.asList("Niflugel", "Paracetamol"),personToUpdate.getMedicalRecord().getMedication());
		Assert.assertEquals(Arrays.asList("Shellfish"),personToUpdate.getMedicalRecord().getAllergies());
	}

	/**
	 * Test to verify the deletion of the medical record for a specific person
	 */
	@Test
	void deleteMedicalRecordForASpecificPersonTest() {

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";

		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDaoMedicalRecord.deleteMedicalRecord(firstName, lastName);

		//THEN
		Assert.assertTrue(persons.get(9).getMedicalRecord().getMedication()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
		Assert.assertTrue(persons.get(9).getMedicalRecord().getAllergies()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
	}
}
