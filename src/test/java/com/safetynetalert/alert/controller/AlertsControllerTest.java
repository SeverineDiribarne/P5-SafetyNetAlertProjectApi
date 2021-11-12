package com.safetyNetAlert.Alert.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.controller.AlertsController;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.model.PersonsByStationId;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAO;

@WebMvcTest(AlertsController.class)
class AlertsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SafetyAlertsInMemoryDAO safetyAlertsDAO;
	
	private final static Logger log = LogManager.getLogger(); 

	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
	}
	
	/**
	 * Test to verify @GetMapping /fireStation
	 * @throws Exception
	 */
	@Test
	void testShouldGetPersonsListCoveredByTheFireStation() throws Exception{

		//GIVEN
		String stationNumber = "1";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		MvcResult result = this.mockMvc.perform(get("/firestation").param("stationNumber", stationNumber))
				.andExpect(status().isOk()).andReturn();

		//THEN
		String content = result.getResponse().getContentAsString();
		try
		{
			PersonsByStationId personsByStationId = new ObjectMapper().readValue(content, PersonsByStationId.class);
			Assert.assertEquals(2,personsByStationId.getNumberOfAdults());
			Assert.assertEquals(1,personsByStationId.getNumberOfChild());
			Assert.assertEquals("Jane",personsByStationId.getPersons().get(0).getFirstName());
			Assert.assertEquals("Doe",personsByStationId.getPersons().get(1).getLastName());
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			log.error("Erreur de traitement :  ", processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /childAlert
	 * @throws Exception
	 */
	@Test
	void testShouldGetChildrenListLivingAtTheAddress() throws Exception{

		//GIVEN
		String address = "290 MullowLand Dr";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		MvcResult result = this.mockMvc.perform(get("/childAlert").param("address", address))
				.andExpect(status().isOk()).andReturn();

		//THEN
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			Assert.assertEquals("Kira",persons.get(0).getFirstName());
			Assert.assertEquals("Doe",persons.get(0).getLastName());
		}
		catch(JsonMappingException mappingException)
		{
			
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /phoneAlert
	 * @throws Exception
	 */
	@Test
	void testShouldGetPhoneListOfInhabitantsCoveredByTheFireStation() throws Exception{

		//GIVEN
		String firestation = "1";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/phoneAlert").param("firestation", firestation))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		try
		{
			List<String> phoneNumbers  = Arrays.asList(new ObjectMapper().readValue(content, String[].class));
			Assert.assertEquals("480-461-7789",phoneNumbers.get(0));
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /fire
	 * @throws Exception
	 */
	@Test
	void testShouldGetListOfInhabitantsLivingAtTheGivenAddressAsWellAsFirestationNumberServingIt() throws Exception{

		//GIVEN
		String address = "290 MullowLand Dr";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/fire").param("address", address))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons  = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			Assert.assertEquals(persons.get(0).getAddress().getStreet(),address);
			Assert.assertTrue(persons.get(0).getAddress().containsFirestationId(1));
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /flood/stations
	 * @throws Exception
	 */
	@Test
	void testShouldGetListAllOfHomesCoveredByTheFireStation() throws Exception{

		//GIVEN
		String[] stations = {"1","2"};
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/flood/stations").param("stations", stations))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons  = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			Assert.assertTrue(persons.get(0).getAddress().getFireStationIds().contains(1) );
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /personInfo
	 * @throws Exception
	 */
	@Test
	void testShouldGetLastNameFirstNameAddressAgeEmailAndMedicalRecordOfEachInhabitants() throws Exception{

		//GIVEN
		String firstName = "Jane";
		String lastName = "Doe";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/personInfo").param("firstName", firstName).param("lastName", lastName))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			Person person  = new ObjectMapper().readValue(content, Person.class);
			Assert.assertEquals("Jane",person.getFirstName());
			Assert.assertEquals("Doe",person.getLastName());
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /communityEmail
	 * @throws Exception
	 */
	@Test
	void testShouldGetEmailListOfInhabitantsOfTheCity() throws Exception{

		//GIVEN
		String city = "Culver";
		List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/communityEmail").param("city", city))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<String> persons  = Arrays.asList(new ObjectMapper().readValue(content, String[].class));
			Assert.assertEquals("janedoe@gmail.com",persons.get(0));
			Assert.assertEquals("laurentdoe@gmail.com",persons.get(2));
		}
		catch(JsonMappingException mappingException)
		{
			log.error("Erreur de mappage Json : ", mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	
}
