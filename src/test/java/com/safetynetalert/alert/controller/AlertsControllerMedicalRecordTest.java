package com.safetyNetAlert.Alert.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import com.safetynetalert.alert.controller.AlertsControllerMedicalRecord;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOMedicalRecord;

@WebMvcTest(AlertsControllerMedicalRecord.class)
class AlertsControllerMedicalRecordTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SafetyAlertsInMemoryDAOMedicalRecord safetyAlertsDAOMedicalRecord;
	
	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
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
	
		/**
		 * Test to verify @PostMapping /medicalRecord
		 * @throws Exception
		 */
		@Test
		void testShouldAddAMedicalRecordToAPerson() throws Exception{

			//GIVEN
			int personId = 8;
			MedicalRecord newMedicalRecord = creationANewMedicalRecord();
			String medicalRecordToAdd = JsonStream.serialize(newMedicalRecord);

			//WHEN
			ArgumentCaptor<Integer> argumentCaptorPersonId = ArgumentCaptor.forClass(Integer.class);
			ArgumentCaptor<MedicalRecord> argumentCaptorMedicalRecord = ArgumentCaptor.forClass(MedicalRecord.class);
			try
			{
			doReturn(true).when(this.safetyAlertsDAOMedicalRecord).addMedicalRecord( argumentCaptorPersonId.capture(), argumentCaptorMedicalRecord.capture());
			} catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
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
		
		/**
		 * @param personToUpdate
		 * @return person to convert
		 */
		private String conversionToJson(Person personToUpdate) {
			String personToUpdateConvertedToJson = JsonStream.serialize(personToUpdate);
			return personToUpdateConvertedToJson;
		}
		
		/**
		 * Test to verify @PutMapping /medicalRecord
		 * @throws Exception
		 */
		@Test
		void testShouldUpdateMedicalRecordToASpecificPerson() throws Exception{
			
			//GIVEN
			String firstName = "Jane";
			String lastName = "Doe";
			List<Person> mockPersonsList = DataTestsHelper.creationPersonsForMock();

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
			doReturn(true).when(this.safetyAlertsDAOMedicalRecord).updateMedicalRecord( argumentCaptorFirstName.capture(),
					argumentCaptorLastName.capture(), argumentCaptorPersonToUpdate.capture());

			//THEN
			this.mockMvc.perform(put("/medicalRecord")
					.param("firstName", firstName)
					.param("lastName", lastName)
					.contentType(MediaType.APPLICATION_JSON)
					.content(personToUpdateConvertedToJson))
			.andExpect(status().isOk()).andReturn();
			Assert.assertEquals(argumentCaptorFirstName.getValue(),firstName);
			Assert.assertEquals(argumentCaptorLastName.getValue(),lastName);
			assert(argumentCaptorPersonToUpdate.getValue().getMedicalRecord().getMedication().equals(personToUpdate.getMedicalRecord().getMedication()));
			assert(argumentCaptorPersonToUpdate.getValue().getMedicalRecord().getAllergies().equals(personToUpdate.getMedicalRecord().getAllergies()));
		}
		
	/**
	 * Test to verify @DeleteMapping /medicalRecord
	 * @throws Exception
	 */
	@Test
	void testShouldDeleteMedicalRecordOfASpecificPerson() throws Exception{

		//GIVEN
		String firstName = "John";
		String lastName = "Boyd";

		//WHEN
		ArgumentCaptor<String> argumentCaptorFirstName = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> argumentCaptorLastName = ArgumentCaptor.forClass(String.class);
		doReturn(true).when(this.safetyAlertsDAOMedicalRecord).deleteMedicalRecord( argumentCaptorFirstName.capture(),argumentCaptorLastName.capture() );

		//THEN
		this.mockMvc.perform(delete("/medicalRecord").param("firstName", firstName).param("lastName", lastName))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(argumentCaptorFirstName.getValue(),firstName);
		Assert.assertEquals(argumentCaptorLastName.getValue(),lastName);
	}
}
