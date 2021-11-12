package com.safetyNetAlert.Alert.helpers;

import java.util.Arrays;
import java.util.List;

import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;

public class DataTestsHelper {

	/**
	 * Creation of 3 persons for mock 
	 * @return
	 */
	public static List<Person> creationPersonsForMock() {
		Person person1 = new Person();
		person1.setId(23);
		person1.setFirstName("Jane");
		person1.setLastName("Doe");
		person1.setAddress(new Address());
		person1.getAddress().setStreet("290 MullowLand Dr"); 
		person1.getAddress().setCity("Culver");
		person1.getAddress().setZip("33000");
		person1.getAddress().setFireStationIds(Arrays.asList(1));
		person1.setBirthDate("10/29/1980");
		person1.setEmail("janedoe@gmail.com");
		person1.setPhone("480-461-7789");
		person1.setMedicalRecord(new MedicalRecord());
		person1.getMedicalRecord().setMedication(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg"));
		person1.getMedicalRecord().setAllergies(Arrays.asList("seafood" , "gluten"));

		Person person2 = new Person();
		person2.setId(24);
		person2.setFirstName("Laurent");
		person2.setLastName("Doe");
		person2.setAddress(new Address());
		person2.getAddress().setStreet("290 MullowLand Dr"); 
		person2.getAddress().setCity("Culver");
		person2.getAddress().setZip("33000");
		person2.getAddress().setFireStationIds(Arrays.asList(1));
		person2.setBirthDate("04/16/1980");
		person2.setEmail("laurentdoe@gmail.com");
		person2.setPhone("480-461-7790");
		person2.setMedicalRecord(new MedicalRecord());
		person2.getMedicalRecord().setMedication(Arrays.asList("lamital:75mg"));
		person2.getMedicalRecord().setAllergies(Arrays.asList("chocolate" , "gluten"));

		Person person3 = new Person();
		person3.setId(25);
		person3.setFirstName("Kira");
		person3.setLastName("Doe");
		person3.setAddress(new Address());
		person3.getAddress().setStreet("290 MullowLand Dr"); 
		person3.getAddress().setCity("Culver");
		person3.getAddress().setZip("33000");
		person3.getAddress().setFireStationIds(Arrays.asList(1));
		person3.setBirthDate("06/04/2016");
		person3.setEmail("laurentdoe@gmail.com");
		person3.setPhone("480-461-7790");
		person3.setMedicalRecord(new MedicalRecord());
		person3.getMedicalRecord().setMedication(Arrays.asList(""));
		person3.getMedicalRecord().setAllergies(Arrays.asList(""));
		List<Person> mockPersonsList = Arrays.asList(person1, person2, person3);
		return mockPersonsList;
	}

	public static void initialiseRepository() {
		RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
		repository.initialize();
	}
	
	/**
	 * Create a new person for test
	 * @return Person to add
	 */
	public static Person creationANewPerson() {

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
	
}
