package com.safetyNetAlert.Alert.SafetyAlertsDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.service.SafetyAlertsDAO;
import com.safetynetalert.alert.service.SafetyAlertsDAOPerson;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOPerson;

@WebMvcTest(SafetyAlertsDAOPersonTest.class)
class SafetyAlertsDAOPersonTest {
	
	SafetyAlertsDAOPerson safetyAlertDaoPerson = new SafetyAlertsInMemoryDAOPerson();
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
		 * Test to verify an addition of a new person in the list
		 * @throws Exception
		 */
		@Test
		void addPersonTest() throws Exception{

			//GIVEN
			//adding a new person in the list
			Person personToAdd = DataTestsHelper.creationANewPerson();

			//WHEN
			// add this person in the list
			safetyAlertDaoPerson.addPersons(personToAdd);

			//THEN
			// check the new person in the list
			List <Person> result = safetyAlertDao.getPersons();
			boolean personFound = false;
			for(Person person : result){
				if(person.getFirstName().contains("Jane")&& person.getLastName().contains("Doe")) {
					personFound = true;	
				}
			}
			Assert.assertEquals(true,personFound);
		}

		/**
		 * Test to verify the modification of information of a person in the list
		 * @throws Exception
		 */
		@Test
		void updatePersonTest()throws Exception{

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
			safetyAlertDaoPerson.updatePerson(firstName, lastName, personToUpdate);

			//WHEN
			Assert.assertEquals("290 MullowLand Dr",personToUpdate.getAddress().getStreet());
			Assert.assertEquals("Culver",personToUpdate.getAddress().getCity());
			Assert.assertEquals("33000",personToUpdate.getAddress().getZip());
			Assert.assertEquals("480-461-7789",personToUpdate.getPhone());
			Assert.assertTrue(personToUpdate.getMedicalRecord().getAllergies().containsAll(Arrays.asList("seafood" , "gluten")));
			Assert.assertTrue(personToUpdate.getMedicalRecord().getMedication().containsAll(Arrays.asList("Ixprim:250mg" , "Ibuprofene:1000mg")));
		}

		/**
		 * Test to verify the deletion of a person from the list	 
		 * @throws Exception
		 */
		@Test
		void deletePersonTest()throws Exception{

			safetyAlertDaoPerson.deletePerson("John", "Boyd");

			List<Person> resultList = safetyAlertDao.getPersons();
			boolean personFound = false;

			for(Person person : resultList) {
				if (person.getFirstName().contains("John") && person.getLastName().contains("Boyd")) {
					personFound = true;
				}
			}
			Assert.assertFalse(personFound);
		}
}
