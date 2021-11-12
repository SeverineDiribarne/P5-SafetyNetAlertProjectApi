package com.safetyNetAlert.Alert.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jsoniter.output.JsonStream;
import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.controller.AlertsControllerPerson;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOPerson;

@WebMvcTest(AlertsControllerPerson.class)
class AlertsControllerPersonTest  {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SafetyAlertsInMemoryDAOPerson safetyAlertsDAOPerson;
	
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

		Person personToAdd = new Person();
		personToAdd.setId(26);
		personToAdd.setFirstName("Jasmina");
		personToAdd.setLastName("Doe");
		personToAdd.setAddress(new Address());
		personToAdd.getAddress().setStreet("290 MullowLand Dr"); 
		personToAdd.getAddress().setCity("Culver");
		personToAdd.getAddress().setZip("33000");
		personToAdd.getAddress().setFireStationIds(Arrays.asList(1));
		personToAdd.setBirthDate("01/02/1999");
		personToAdd.setEmail("jasminadoe@gmail.com");
		personToAdd.setPhone("480-461-7896");
		personToAdd.setMedicalRecord(new MedicalRecord());
		personToAdd.getMedicalRecord().setMedication(Arrays.asList(""));
		personToAdd.getMedicalRecord().setAllergies(Arrays.asList("Lactose"));

		return personToAdd;	
	}
	
	/**
	 * Test to verify @PostMapping /person
	 * @throws Exception
	 */
	@Test
	void testShouldAddANewPersonOnTheJsonListOfPersons() throws Exception{

		//GIVEN
		Person newPerson = creationANewPerson();
		String personAdding = JsonStream.serialize(newPerson);

		//WHEN
		ArgumentCaptor<Person> valueCapture = ArgumentCaptor.forClass(Person.class);
		doReturn(true).when(this.safetyAlertsDAOPerson).addPersons( valueCapture.capture());

		//THEN
		this.mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personAdding))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(valueCapture.getValue().getFirstName(),newPerson.getFirstName());
		Assert.assertEquals(valueCapture.getValue().getLastName(),newPerson.getLastName());
		Assert.assertEquals(valueCapture.getValue().getBirthDate(),newPerson.getBirthDate());
		Assert.assertEquals(valueCapture.getValue().getEmail(),newPerson.getEmail());
		Assert.assertEquals(valueCapture.getValue().getPhone(),newPerson.getPhone());
		Assert.assertEquals(valueCapture.getValue().getAddress().getStreet(),newPerson.getAddress().getStreet());
		Assert.assertEquals(valueCapture.getValue().getAddress().getCity(),newPerson.getAddress().getCity());
		Assert.assertEquals(valueCapture.getValue().getAddress().getZip(),newPerson.getAddress().getZip());
		Assert.assertEquals(valueCapture.getValue().getAddress().getFireStationIds(),newPerson.getAddress().getFireStationIds());
		Assert.assertEquals(valueCapture.getValue().getMedicalRecord().getMedication(),newPerson.getMedicalRecord().getMedication());
		Assert.assertEquals(valueCapture.getValue().getMedicalRecord().getAllergies(),newPerson.getMedicalRecord().getAllergies());
	}
	
	/**
	 * @param personToUpdate
	 * @return person to convert
	 */
	private String conversionToJson(Person personToUpdate) {
		String personToUpdateConvertedToJson = JsonStream.serialize(personToUpdate);
		return personToUpdateConvertedToJson;
	}

	/**
	 * Test to verify @PutMapping /person
	 * @throws Exception
	 */
	@Test
	void testShouldUpdateASpecificPersonOfTheJsonListOfPersons() throws Exception{

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";
		Person personToUpdate = new Person();
		Address addressOfThePersonToUpdate = new Address();

		personToUpdate.setBirthDate("04/29/1946");		
		addressOfThePersonToUpdate.setStreet("951 LoneTree Rd");
		addressOfThePersonToUpdate.setFireStationIds(Arrays.asList(2));
		personToUpdate.setAddress(addressOfThePersonToUpdate);
		personToUpdate.setPhone("841-874-7458");

		String personToUpdateConvertedToJson = conversionToJson(personToUpdate);

		//WHEN
		ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Person> argumentCaptorPersonToUpdate = ArgumentCaptor.forClass(Person.class);
		doReturn(true).when(this.safetyAlertsDAOPerson).updatePerson( argumentCaptorFirstName.capture(),
				argumentCaptorLastName.capture(), argumentCaptorPersonToUpdate.capture());

		//THEN
		this.mockMvc.perform(put("/person")
				.param("firstName", firstName)
				.param("lastName", lastName)
				.contentType(MediaType.APPLICATION_JSON)
				.content(personToUpdateConvertedToJson))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(argumentCaptorFirstName.getValue(),firstName);
		Assert.assertEquals(argumentCaptorLastName.getValue(),lastName);
		Assert.assertEquals(argumentCaptorPersonToUpdate.getValue().getBirthDate(),personToUpdate.getBirthDate());
		Assert.assertEquals(argumentCaptorPersonToUpdate.getValue().getPhone(),personToUpdate.getPhone());		 
		Assert.assertEquals(argumentCaptorPersonToUpdate.getValue().getAddress().getStreet(),personToUpdate.getAddress().getStreet());
		Assert.assertEquals(argumentCaptorPersonToUpdate.getValue().getAddress().getFireStationIds(),personToUpdate.getAddress().getFireStationIds());
	}

	/**
	 * Test to verify @DeleteMapping /person
	 * @throws Exception
	 */
	@Test
	void  testShouldDeleteASpecificPersonOfTheJsonListOfPersons() throws Exception{

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";

		//WHEN
		ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
		doReturn(true).when(this.safetyAlertsDAOPerson).deletePerson( argumentCaptorFirstName.capture(), argumentCaptorLastName.capture());

		//THEN
		this.mockMvc.perform(delete("/person")
				.param("firstName", firstName)
				.param("lastName", lastName))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(argumentCaptorFirstName.getValue(),firstName);
		Assert.assertEquals(argumentCaptorLastName.getValue(),lastName);
	}
}
