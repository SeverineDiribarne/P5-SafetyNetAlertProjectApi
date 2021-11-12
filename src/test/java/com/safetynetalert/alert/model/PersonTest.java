package com.safetyNetAlert.Alert.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.safetynetalert.alert.model.Person;

class PersonTest {
	
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
	void TestToVerifyTheAgeCalculationMethod() {
		Long expectedAge = (long) 5;
		//GIVEN
		Person person = creationANewPerson();
		
		//WHEN
		Long age = person.ageCalculation();
		
		//THEN
		Assert.assertEquals(age,expectedAge);
	}
}
