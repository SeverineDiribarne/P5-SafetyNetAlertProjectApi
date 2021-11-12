package com.safetyNetAlert.Alert.repository;

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
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.repository.RepositoryMedicalRecord;

@WebMvcTest(RepositoryMedicalRecordTest.class)
class RepositoryMedicalRecordTest {

	private RepositoryMedicalRecord repositoryMedicalRecord = new RepositoryMedicalRecord();

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

		//WHEN
		repositoryMedicalRecord.addMedicalRecord(personId, medicalRecord);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
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
		Person personBody = new Person();
		MedicalRecord medicalRecordBody = new MedicalRecord();
		medicalRecordBody.setMedication(Arrays.asList("Niflugel", "Paracetamol"));
		medicalRecordBody.setAllergies(Arrays.asList("Oyster"));
		personBody.setMedicalRecord(medicalRecordBody);

		//WHEN
		repositoryMedicalRecord.updateMedicalRecord(firstName, lastName, personBody);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		for(Person person : persons) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				Assert.assertTrue(person.getMedicalRecord().getMedication().containsAll(Arrays.asList("Niflugel", "Paracetamol")) );
				Assert.assertTrue(person.getMedicalRecord().getAllergies().containsAll(Arrays.asList("Oyster")));
			}
		}
	}

	/**
	 * Test to verify the deletion of the medical record for a specific person
	 */
	@Test
	void deleteMedicalRecordForASpecificPersonTest() {

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";

		//WHEN
		repositoryMedicalRecord.deleteMedicalRecord(firstName, lastName);

		//THEN
		List<Person> persons = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
		Assert.assertTrue(persons.get(9).getMedicalRecord().getMedication()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
		Assert.assertTrue(persons.get(9).getMedicalRecord().getAllergies()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
	}
}
