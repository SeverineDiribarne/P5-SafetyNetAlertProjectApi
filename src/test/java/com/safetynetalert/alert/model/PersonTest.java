package com.safetyNetAlert.Alert.model;

import org.junit.jupiter.api.Test;

import com.safetynetalert.alert.model.Person;

public class PersonTest {

	Person person = new Person(); 
	
	/**
	 * Create a new person for test
	 * @return Person to add
	 */
	public Person creationANewPerson() {

		// creation new person to add
		Person personToAdd = new Person();
		personToAdd.setId(27);
		personToAdd.setFirstName("Erwan");
		personToAdd.setLastName("Doe");
		personToAdd.setBirthDate("01/26/2016");

		return personToAdd;	
	}
	
	/**
	 * Test to verify Age Calculation method
	 */
	@Test
	public void TestToVerifyTheAgeCalculationMethod() {
		//TODO Method to complete
		//GIVEN
		Person erwan = creationANewPerson();
		
		//WHEN
		Long age = erwan.ageCalculation();
		
		//THEN
		assert(age.compareTo((long) 5)==0);
	}
}
