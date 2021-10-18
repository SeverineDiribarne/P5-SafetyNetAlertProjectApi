package com.safetynetalert.alert.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import com.jsoniter.output.JsonStream;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.model.PersonsByStationId;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import com.safetynetalert.alert.service.SafetyAlertsDAO;

@WebMvcTest(AlertsController.class)
public class AlertsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SafetyAlertsDAO safetyAlertsDAO;

	/**
	 * Initialize the json list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
		repository.initialize();
	}

	//	/**
	//	 * clean the list after each Test
	//	 */
	//	@AfterAll
	//	public static void tearDown(){
	//		List<Person> result = RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
	//		result.clear();
	//	}
	
	/**
	 * Creation of 3 persons for mock 
	 * @return
	 */
	private List<Person> creationPersonsForMock() {
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

	/**
	 * Create a new person for test
	 * @return Person to add
	 */
	public Person creationANewPerson() {

		// creation new person to add
		Person personToAdd = new Person();
		personToAdd.setId(26);
		personToAdd.setFirstName("Jasmina");
		personToAdd.setLastName("Doe");
		personToAdd.setAddress(new Address());
		personToAdd.getAddress().setStreet("290 MullowLand Dr"); 
		personToAdd.getAddress().setCity("Culver");
		personToAdd.getAddress().setZip("33000");
		personToAdd.setBirthDate("01/02/1999");
		personToAdd.setEmail("jasminadoe@gmail.com");
		personToAdd.setPhone("480-461-7896");
		personToAdd.setMedicalRecord(new MedicalRecord());
		personToAdd.getMedicalRecord().setMedication(Arrays.asList(""));
		personToAdd.getMedicalRecord().setAllergies(Arrays.asList("Lactose"));

		return personToAdd;	
	}
	
	//-------------------------------------GET REQUEST-----------------------------------------------

	/**
	 * Test to verify @GetMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldGetPersonsListCoveredByTheFireStation() throws Exception{
		
		//GIVEN
		String stationNumber = "1";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		MvcResult result = this.mockMvc.perform(get("/firestation").param("stationNumber", stationNumber))
				.andExpect(status().isOk()).andReturn();

		//THEN
		String content = result.getResponse().getContentAsString();
		try
		{
			PersonsByStationId personsByStationId = new ObjectMapper().readValue(content, PersonsByStationId.class);
			assert(personsByStationId.getNumberOfAdults()==2);
			assert(personsByStationId.getNumberOfChild()==1);
			assert(personsByStationId.getPersons().get(0).getFirstName().compareTo("Jane")== 0);
			assert(personsByStationId.getPersons().get(1).getLastName().compareTo("Doe")== 0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @GetMapping /childAlert
	 * @throws Exception
	 */
	@Test
	public void testShouldGetChildrenListLivingAtTheAddress() throws Exception{
		
		//GIVEN
		String address = "290 MullowLand Dr";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		MvcResult result = this.mockMvc.perform(get("/childAlert").param("address", address))
				.andExpect(status().isOk()).andReturn();
		//THEN
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			assert(persons.get(0).getFirstName().compareTo("Kira")== 0);
			assert(persons.get(0).getLastName().compareTo("Doe")== 0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
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
	public void testShouldGetPhoneListOfInhabitantsCoveredByTheFireStation() throws Exception{

		//GIVEN
		String firestation = "1";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		
		//THEN
		MvcResult result = this.mockMvc.perform(get("/phoneAlert").param("firestation", firestation))
		.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		try
		{
			List<String> phoneNumbers  = Arrays.asList(new ObjectMapper().readValue(content, String[].class));
			assert(phoneNumbers.get(0).compareTo("480-461-7789") == 0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
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
	public void testShouldGetListOfInhabitantsLivingAtTheGivenAddressAsWellAsFirestationNumberServingIt() throws Exception{
		
		//GIVEN
		String address = "290 MullowLand Dr";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/fire").param("address", address))
		.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons  = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			assert(persons.get(0).getAddress().getStreet().equals(address));
			assert(persons.get(0).getAddress().containsFirestationId(1)== true);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
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
	public void testShouldGetListAllOfHomesCoveredByTheFireStation() throws Exception{

		//GIVEN
		String[] stations = {"1","2"};
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/flood/stations").param("stations", stations))
		.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<Person> persons  = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			assert(persons.get(0).getAddress().getFireStationIds().contains(1) );
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
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
	public void testShouldGetLastNameFirstNameAddressAgeEmailAndMedicalRecordOfEachInhabitants() throws Exception{
		
		//GIVEN
		String firstName = "Jane";
		String lastName = "Doe";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/personInfo").param("firstName", firstName).param("lastName", lastName))
		.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			Person person  = new ObjectMapper().readValue(content, Person.class);
			assert(person.getFirstName().compareTo("Jane")==0);
			assert(person.getLastName().compareTo("Doe")==0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
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
	public void testShouldGetEmailListOfInhabitantsOfTheCity() throws Exception{

		//GIVEN
		String city = "Culver";
		List<Person> mockPersonsList = creationPersonsForMock();
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);

		//THEN
		MvcResult result = this.mockMvc.perform(get("/communityEmail").param("city", city))
		.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		try
		{
			List<String> persons  = Arrays.asList(new ObjectMapper().readValue(content, String[].class));
			assert(persons.get(0).compareTo("janedoe@gmail.com") == 0);
			assert(persons.get(2).compareTo("laurentdoe@gmail.com") == 0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	//-------------------------------------POST REQUEST---------------------------------------------

	/**
	 * Test to verify @PostMapping /person
	 * @throws Exception
	 */
	@Test
	public void testShouldAddANewPersonOnTheJsonListOfPersons() throws Exception{
		//TODO Méthode à rectifier NE FONCTIONNE PAS erreur à ligne 411 (entre dans une exception)
		//GIVEN
		//adding a new person in the list
		String personAdding = JsonStream.serialize(creationANewPerson());
		List<Person> mockPersonsList = creationPersonsForMock();
		
		//WHEN
		when(this.safetyAlertsDAO.getPersons()).thenReturn(mockPersonsList);
		
		//THEN
		MvcResult result = this.mockMvc.perform(post("/person").content(personAdding))
		.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		
		try
		{
			List<Person> persons  = Arrays.asList(new ObjectMapper().readValue(content, Person[].class));
			assert(persons.get(3).getFirstName().compareTo("Jasmina") == 0);
			assert(persons.get(3).getLastName().compareTo("Doe") == 0);
		}
		catch(JsonMappingException mappingException)
		{
			System.err.println(mappingException.getMessage());
			assert(false);
		}
		catch(JsonProcessingException processingException)
		{
			System.err.println(processingException.getMessage());
			assert(false);
		}
	}

	/**
	 * Test to verify @PostMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldAddAMappingBetweenFirestationAndAddress() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(post("/firestation"))
		.andExpect(status().isOk());
	}

	/**
	 * Test to verify @PostMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	public void testShouldAddAMedicalRecordToAPerson() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(post("/medicalRecord"))
		.andExpect(status().isOk());
	}

	//-------------------------------------UPDATE REQUEST-------------------------------------------

	/**
	 * Test to verify @PutMapping /person
	 * @throws Exception
	 */
	@Test
	public void testShouldUpdateASpecificPersonOfTheJsonListOfPersons() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(put("/person"))
		.andExpect(status().isOk());
	}

	/**
	 * Test to verify @PutMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldUpdateTheFirestationNumberToAnAddress() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(put("/firestation"))
		.andExpect(status().isOk());
	}

	/**
	 * Test to verify @PutMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	public void testShouldUpdateMedicalRecordToASpecificPerson() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(put("/medicalRecord"))
		.andExpect(status().isOk());
	}

	//-------------------------------------DELETE REQUEST-------------------------------------------

	/**
	 * Test to verify @DeleteMapping /person
	 * @throws Exception
	 */
	@Test
	public void  testShouldDeleteASpecificPersonOfTheJsonListOfPersons() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(delete("/person"))
		.andExpect(status().isOk());
	}

	/**
	 * Test to verify @DeleteMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldDeleteMappingOfAFirestationOrOfAnAddress() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(delete("/firestation"))
		.andExpect(status().isOk());
	}

	/**
	 * Test to verify @DeleteMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	public void testShouldDeleteMedicalRecordOfASpecificPerson() throws Exception{
		//TODO Méthode à completer
		//GIVEN

		//WHEN

		//THEN
		this.mockMvc.perform(delete("/medicalRecord"))
		.andExpect(status().isOk());
	}
}
