package com.safetyNetAlert.Alert.controller;

import static org.mockito.Mockito.doNothing;
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
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.output.JsonStream;
import com.safetynetalert.alert.controller.AlertsController;
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
	 * Create a new medicalRecord for test
	 * @return MedicalRecord to add
	 */
	public MedicalRecord creationANewMedicalRecord() {

		// creation new person to add
		MedicalRecord medicalRecordToAdd = new MedicalRecord();
		medicalRecordToAdd.setMedication(Arrays.asList("Aspirine : 1000mg"));
		medicalRecordToAdd.setAllergies(Arrays.asList("Lactose"));


		return medicalRecordToAdd;	
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

		//GIVEN
		Person newPerson = creationANewPerson();
		String personAdding = JsonStream.serialize(newPerson);

		//WHEN
		ArgumentCaptor<Person> valueCapture = ArgumentCaptor.forClass(Person.class);
		doNothing().when(this.safetyAlertsDAO).addPersons( valueCapture.capture());

		//THEN
		this.mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personAdding))
		.andExpect(status().isOk()).andReturn();
		assert(valueCapture.getValue().getFirstName().compareTo(newPerson.getFirstName())==0);
		assert(valueCapture.getValue().getLastName().compareTo(newPerson.getLastName())==0);
		assert(valueCapture.getValue().getBirthDate().compareTo(newPerson.getBirthDate())==0);
		assert(valueCapture.getValue().getEmail().compareTo(newPerson.getEmail())==0);
		assert(valueCapture.getValue().getPhone().compareTo(newPerson.getPhone())==0);
		assert(valueCapture.getValue().getAddress().getStreet().compareTo(newPerson.getAddress().getStreet())==0);
		assert(valueCapture.getValue().getAddress().getCity().compareTo(newPerson.getAddress().getCity())==0);
		assert(valueCapture.getValue().getAddress().getZip().compareTo(newPerson.getAddress().getZip())==0);
		assert(valueCapture.getValue().getAddress().getFireStationIds().equals(newPerson.getAddress().getFireStationIds()));
		assert(valueCapture.getValue().getMedicalRecord().getMedication().equals(newPerson.getMedicalRecord().getMedication()));
		assert(valueCapture.getValue().getMedicalRecord().getAllergies().equals(newPerson.getMedicalRecord().getAllergies()));

	}

	/**
	 * Test to verify @PostMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldAddAMappingBetweenFirestationAndAddress() throws Exception{

		//GIVEN
		Integer firestationId = 1;
		String address = "1509 Culver St";

		//WHEN
		ArgumentCaptor<Integer> argumentCaptorFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		doNothing().when(this.safetyAlertsDAO).addMapping( argumentCaptorFirestationId.capture(), argumentCaptorAddress.capture());

		//THEN
		this.mockMvc.perform(post("/firestation")
				.param("firestationId", "1")
				.param("address", address))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorFirestationId.getValue().compareTo(firestationId)==0);
		assert(argumentCaptorAddress.getValue().compareTo(address)==0);

	}

	/**
	 * Test to verify @PostMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	public void testShouldAddAMedicalRecordToAPerson() throws Exception{

		//GIVEN
		int personId = 8;
		MedicalRecord newMedicalRecord = creationANewMedicalRecord();
		String medicalRecordToAdd = JsonStream.serialize(newMedicalRecord);

		//WHEN
		ArgumentCaptor<Integer> argumentCaptorPersonId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<MedicalRecord> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(MedicalRecord.class);
		doNothing().when(this.safetyAlertsDAO).addMedicalRecord( argumentCaptorPersonId.capture(), argumentCaptorMedicalRecord.capture());

		//THEN
		this.mockMvc.perform(post("/medicalRecord")
				.param("personId", "8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(medicalRecordToAdd))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorPersonId.getValue().compareTo(personId)==0);
		assert(argumentCaptorMedicalRecord.getValue().getMedication().equals(newMedicalRecord.getMedication()));
		assert(argumentCaptorMedicalRecord.getValue().getAllergies().equals(newMedicalRecord.getAllergies()));
	}

	//-------------------------------------UPDATE REQUEST-------------------------------------------

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
	public void testShouldUpdateASpecificPersonOfTheJsonListOfPersons() throws Exception{

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
		doNothing().when(this.safetyAlertsDAO).updatePerson( argumentCaptorFirstName.capture(),
				argumentCaptorLastName.capture(), argumentCaptorPersonToUpdate.capture());

		//THEN
		this.mockMvc.perform(put("/person")
				.param("firstName", firstName)
				.param("lastName", lastName)
				.contentType(MediaType.APPLICATION_JSON)
				.content(personToUpdateConvertedToJson))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorFirstName.getValue().compareTo(firstName)==0);
		assert(argumentCaptorLastName.getValue().compareTo(lastName)==0);
		assert(argumentCaptorPersonToUpdate.getValue().getBirthDate().compareTo(personToUpdate.getBirthDate()) == 0);
		assert(argumentCaptorPersonToUpdate.getValue().getPhone().compareTo(personToUpdate.getPhone()) == 0);		 
		assert(argumentCaptorPersonToUpdate.getValue().getAddress().getStreet().compareTo(personToUpdate.getAddress().getStreet()) == 0);
		assert(argumentCaptorPersonToUpdate.getValue().getAddress().getFireStationIds().equals(personToUpdate.getAddress().getFireStationIds()));
	}



	/**
	 * Test to verify @PutMapping /firestation
	 * @throws Exception
	 */
	@Test
	public void testShouldUpdateTheFirestationNumberToAnAddress() throws Exception{

		//GIVEN
		String address = "951 LoneTree Rd";
		int oldFirestationId = 4;
		int newFirestationId = 2;

		//WHEN
		ArgumentCaptor<Integer> argumentCaptorOldFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> argumentCaptorNewFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		doNothing().when(this.safetyAlertsDAO).updateFirestationNumber( argumentCaptorOldFirestationId.capture(),
				argumentCaptorNewFirestationId.capture(), argumentCaptorAddress.capture());

		//THEN
		this.mockMvc.perform(put("/firestation")
				.param("oldFirestationId", "4")
				.param("newFirestationId", "2")
				.param("address", address))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorOldFirestationId.getValue().compareTo(oldFirestationId)==0);
		assert(argumentCaptorNewFirestationId.getValue().compareTo(newFirestationId)==0);
		assert(argumentCaptorAddress.getValue().compareTo(address) == 0);
	}

	/**
	 * Test to verify @PutMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	public void testShouldUpdateMedicalRecordToASpecificPerson() throws Exception{
		
		//GIVEN
		String firstName = "Jane";
		String lastName = "Doe";
		List<Person> mockPersonsList = creationPersonsForMock();

		Person personToUpdate = new Person();
		for (Person person : mockPersonsList) {
			if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				personToUpdate = person;
			}
		}
		MedicalRecord medicalRecordOfThePersonToUpdate = new MedicalRecord();
		medicalRecordOfThePersonToUpdate.setMedication(Arrays.asList("Paracetamol : 1000mg"));
		medicalRecordOfThePersonToUpdate.setAllergies(Arrays.asList("Ixprim : 250mg", "Lactose"));
		personToUpdate.setMedicalRecord(medicalRecordOfThePersonToUpdate);

		String personToUpdateConvertedToJson = conversionToJson(personToUpdate);
		//WHEN
		ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Person> argumentCaptorPersonToUpdate = ArgumentCaptor.forClass(Person.class);
		doNothing().when(this.safetyAlertsDAO).updateMedicalRecord( argumentCaptorFirstName.capture(),
				argumentCaptorLastName.capture(), argumentCaptorPersonToUpdate.capture());

		//THEN
		this.mockMvc.perform(put("/medicalRecord")
				.param("firstName", firstName)
				.param("lastName", lastName)
				.contentType(MediaType.APPLICATION_JSON)
				.content(personToUpdateConvertedToJson))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorFirstName.getValue().compareTo(firstName)==0);
		assert(argumentCaptorLastName.getValue().compareTo(lastName)==0);
		assert(argumentCaptorPersonToUpdate.getValue().getMedicalRecord().getMedication().equals(personToUpdate.getMedicalRecord().getMedication()));
		assert(argumentCaptorPersonToUpdate.getValue().getMedicalRecord().getAllergies().equals(personToUpdate.getMedicalRecord().getAllergies()));
	}

//-------------------------------------DELETE REQUEST-------------------------------------------

/**
 * Test to verify @DeleteMapping /person
 * @throws Exception
 */
@Test
public void  testShouldDeleteASpecificPersonOfTheJsonListOfPersons() throws Exception{

	//GIVEN
	String firstName = "John";
	String lastName = "Boyd";

	//WHEN
	ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
	doNothing().when(this.safetyAlertsDAO).deletePerson( argumentCaptorFirstName.capture(), argumentCaptorLastName.capture());

	//THEN
	this.mockMvc.perform(delete("/person")
			.param("firstName", firstName)
			.param("lastName", lastName))
	.andExpect(status().isOk()).andReturn();
	assert(argumentCaptorFirstName.getValue().compareTo(firstName)==0);
	assert(argumentCaptorLastName.getValue().compareTo(lastName)==0);
}

/**
 * Test to verify @DeleteMapping /firestation
 * @throws Exception
 */
@Test
public void testShouldDeleteMappingOfAFirestation() throws Exception{

	//GIVEN
	Integer firestationId = 1;
	//WHEN
	ArgumentCaptor<Integer> argumentCaptorFirestationId = ArgumentCaptor.forClass(Integer.class);
	doNothing().when(this.safetyAlertsDAO).deleteMappingByFirestationId( argumentCaptorFirestationId.capture());

	//THEN
	this.mockMvc.perform(delete("/firestation").param("firestationId", "1"))
	.andExpect(status().isOk()).andReturn();
	assert(argumentCaptorFirestationId.getValue().compareTo(firestationId)==0);
}

/**
 * Test to verify @DeleteMapping /firestation
 * @throws Exception
 */
@Test
public void testShouldDeleteMappingOfAnAddress() throws Exception{

	//GIVEN
	String address = "1509 Culver St";

	//WHEN
	ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
	doNothing().when(this.safetyAlertsDAO).deleteMappingByAddress( argumentCaptorAddress.capture());

	//THEN
	this.mockMvc.perform(delete("/firestation").param("address", address))
	.andExpect(status().isOk()).andReturn();
	assert(argumentCaptorAddress.getValue().compareTo(address)==0);
}

/**
 * Test to verify @DeleteMapping /medicalRecord
 * @throws Exception
 */
@Test
public void testShouldDeleteMedicalRecordOfASpecificPerson() throws Exception{

	//GIVEN
	String firstName = "John";
	String lastName = "Boyd";

	//WHEN
	ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
	ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
	doNothing().when(this.safetyAlertsDAO).deleteMedicalRecord( argumentCaptorFirstName.capture(),argumentCaptorLastName.capture() );

	//THEN
	this.mockMvc.perform(delete("/medicalRecord").param("firstName", firstName).param("lastName", lastName))
	.andExpect(status().isOk()).andReturn();
	assert(argumentCaptorFirstName.getValue().compareTo(firstName)==0);
	assert(argumentCaptorLastName.getValue().compareTo(lastName)==0);
}
}
