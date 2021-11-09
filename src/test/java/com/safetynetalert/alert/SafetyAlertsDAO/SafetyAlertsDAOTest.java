package com.safetyNetAlert.Alert.SafetyAlertsDAO;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.service.SafetyAlertsDAO;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;


@WebMvcTest(SafetyAlertsDAOTest.class)
public class SafetyAlertsDAOTest {

	SafetyAlertsDAO safetyAlertDao = new SafetyAlertsInMemoryDAO();

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
	public void getPersonsTest() {

		List<Person>result = safetyAlertDao.getPersons();
		assert( result.get(0).getFirstName().compareTo("John") == 0);	
		assert( result.get(0).getLastName().compareTo("Boyd") == 0);
		assert( result.get(0).getAddress().getStreet().compareTo("1509 Culver St") == 0);
		assert( result.get(0).getAddress().getCity().compareTo("Culver") == 0);
		assert( result.get(0).getAddress().getZip().compareTo("97451") == 0);
		assert( result.get(0).getBirthDate().compareTo("03/06/1984") == 0);
		assert( result.get(0).getEmail().compareTo("jaboyd@email.com") == 0);
		assert( result.get(0).getPhone().compareTo("841-874-6512") == 0);
	}
	/**
	 * Test to verify the existence of firestations in the list
	 */
	@Test
	public void getFirestationsTest() {
		List<FireStation>result = safetyAlertDao.getFireStations();
		assert( result.get(0).getStationId()==3);
	}

	/**
	 * Test to verify the existence of medical records in the list
	 */
	@Test
	public void getMedicalRecordsTest() {
		List<MedicalRecord> result = safetyAlertDao.getMedicalRecords();
		assert ( result.get(0).getMedication().equals(Arrays.asList("aznol:350mg","hydrapermazol:100mg")));
		assert(result.get(0).getAllergies().equals(Arrays.asList("nillacilan")));
	}

	//---------------------------------------Person--------------------------------------------------

	/**
	 * Test to verify an addition of a new person in the list
	 * @throws Exception
	 */
	@Test
	public void addPersonTest() throws Exception{

		//GIVEN
		//adding a new person in the list
		Person personToAdd = creationANewPerson();

		//WHEN
		// add this person in the list
		safetyAlertDao.addPersons(personToAdd);

		//THEN
		// check the new person in the list
		List <Person> result = safetyAlertDao.getPersons();
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

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";

		Person personToUpdate = new Person();
		for (Person person : safetyAlertDao.getPersons()) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				personToUpdate = person;
			}
		}
		personToUpdate.getAddress().setStreet("290 MullowLand Dr"); 
		personToUpdate.getAddress().setCity("Culver");
		personToUpdate.getAddress().setZip("33000");
		personToUpdate.setPhone("480-461-7789");
		personToUpdate.getMedicalRecord().setMedication(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg"));
		personToUpdate.getMedicalRecord().setAllergies(Arrays.asList("seafood" , "gluten"));

		//THEN
		safetyAlertDao.updatePerson(firstName, lastName, personToUpdate);

		//WHEN
		assert(personToUpdate.getAddress().getStreet().compareTo("290 MullowLand Dr")==0);
		assert(personToUpdate.getAddress().getCity().compareTo("Culver")==0);
		assert(personToUpdate.getAddress().getZip().compareTo("33000")==0);
		assert(personToUpdate.getPhone().compareTo("480-461-7789")==0);
		assert(personToUpdate.getMedicalRecord().getAllergies().containsAll(Arrays.asList("seafood" , "gluten")));
		assert(personToUpdate.getMedicalRecord().getMedication().containsAll(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg")));
	}

	/**
	 * Test to verify the deletion of a person from the list	 
	 * @throws Exception
	 */
	@Test
	public void deletePersonTest()throws Exception{

		safetyAlertDao.deletePerson("John", "Boyd");

		List<Person> resultList = safetyAlertDao.getPersons();
		boolean personFound = false;

		for(Person person : resultList) {
			if (person.getFirstName().contains("John") && person.getLastName().contains("Boyd")) {
				personFound = true;
			}
		}
		assert(personFound==false);
	}

	//---------------------------------------Firestation---------------------------------------------

	/**
	 * Test to verify an addition of a mapping of a firestation
	 */
	@Test
	public void addMappingTest() {

		//GIVEN
		int firestationId = 5;
		String address = "1509 Culver St";
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.addMapping(firestationId,address);

		//THEN
		assert(persons.get(0).getAddress().getFireStationIds().contains(5));
		assert(persons.get(0).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(1).getAddress().getFireStationIds().contains(5));
		assert(persons.get(1).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(3).getAddress().getFireStationIds().contains(5));
		assert(persons.get(3).getAddress().getStreet().compareTo(address)==0);
	}

	/**
	 * Test to verify the modification of a mapping of a firestation for a specific address 
	 */
	@Test
	public void updateMappingTest() {

		//GIVEN
		int oldFirestationId = 3;
		int newFirestationId = 5;
		String address = "1509 Culver St";
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.updateFirestationNumber(oldFirestationId, newFirestationId, address);

		//THEN
		assert(persons.get(0).getAddress().getFireStationIds().contains(5));
		assert(persons.get(0).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(1).getAddress().getFireStationIds().contains(5));
		assert(persons.get(1).getAddress().getStreet().compareTo(address)==0);
		assert(persons.get(3).getAddress().getFireStationIds().contains(5));
		assert(persons.get(3).getAddress().getStreet().compareTo(address)==0);
	}

	/**
	 * Test to verify the deletion of a mapping of a firestation 
	 */
	@Test
	public void deleteMappingOfAFirestationTest() {

		//GIVEN
		int firestationId = 3;
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.deleteMappingByFirestationId(firestationId);

		//THEN
		assert(persons.get(0).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(6).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

	/**
	 * Test to verify the deletion of a mapping of a specific address
	 */
	@Test
	public void deleteMappingOfAnAddressTest() {

		//GIVEN
		String address = "748 Townings Dr";
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.deleteMappingByAddress(address);

		//THEN
		assert(persons.get(8).getAddress().getFireStationIds().isEmpty());
		assert(persons.get(21).getAddress().getFireStationIds().isEmpty());
	}

	//--------------------------------------MedicalRecord--------------------------------------------

	/**
	 * Test to verify an addition of a medical record of a specific person
	 */
	@Test
	public void addMedicalRecordForASpecificPersonTest() {

		//GIVEN
		int personId=10; 
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setMedication(Arrays.asList("Aspirin", "Niflugel"));
		medicalRecord.setAllergies(Arrays.asList("Oyster"));
		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.addMedicalRecord(personId, medicalRecord);

		//THEN
		assert(persons.get(10).getMedicalRecord().getMedication().containsAll(Arrays.asList("Aspirin", "Niflugel")));
		assert(persons.get(10).getMedicalRecord().getAllergies().containsAll(Arrays.asList("Oyster")));
	}

	/**
	 * Test to verify the modification of the medical record for a specific person 
	 */
	@Test
	public void updateMedicalRecordForASpecificPersonTest() {

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
		safetyAlertDao.updateMedicalRecord(firstName, lastName, personToUpdate );

		//THEN
		assert(personToUpdate.getMedicalRecord().getMedication().equals(Arrays.asList("Niflugel", "Paracetamol")));
		assert(personToUpdate.getMedicalRecord().getAllergies().equals(Arrays.asList("Shellfish")));
	}

	/**
	 * Test to verify the deletion of the medical record for a specific person
	 */
	@Test
	public void deleteMedicalRecordForASpecificPersonTest() {

		//GIVEN
		String firstName = "Tony";
		String lastName = "Cooper";

		List<Person> persons = safetyAlertDao.getPersons();

		//WHEN
		safetyAlertDao.deleteMedicalRecord(firstName, lastName);

		//THEN
		assert(persons.get(9).getMedicalRecord().getMedication()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
		assert(persons.get(9).getMedicalRecord().getAllergies()== null || persons.get(9).getMedicalRecord().getMedication().isEmpty());
	}
}
