package com.safetyNetAlert.Alert.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetyNetAlert.Alert.helpers.DataTestsHelper;
import com.safetynetalert.alert.controller.AlertsControllerFirestation;
import com.safetynetalert.alert.service.SafetyAlertsInMemoryDAOFirestation;

@WebMvcTest(AlertsControllerFirestation.class)
class AlertsControllerFirestationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SafetyAlertsInMemoryDAOFirestation safetyAlertsDAOFirestation;
		
	/**
	 * Initialise the JSON list before each test
	 * @throws IOException
	 */
	@BeforeEach
	public void setUp() throws IOException {
		DataTestsHelper.initialiseRepository();
	}
	
	/**
	 * Test to verify @PostMapping /fireStation
	 * @throws Exception
	 */
	@Test
	void testShouldAddAMappingBetweenFirestationAndAddress() throws Exception{

		//GIVEN
		Integer firestationId = 1;
		String address = "1509 Culver St";

		//WHEN
		ArgumentCaptor<Integer> argumentCaptorFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		doReturn(true).when(this.safetyAlertsDAOFirestation).addMapping( argumentCaptorFirestationId.capture(), argumentCaptorAddress.capture());

		//THEN
		this.mockMvc.perform(post("/firestation")
				.param("firestationId", "1")
				.param("address", address))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(argumentCaptorFirestationId.getValue(),firestationId);
		Assert.assertEquals(argumentCaptorAddress.getValue(),address);
	}

	/**
	 * Test to verify @PutMapping /fireStation
	 * @throws Exception
	 */
	@Test
	void testShouldUpdateTheFirestationNumberToAnAddress() throws Exception{

		//GIVEN
		String address = "951 LoneTree Rd";
		int oldFirestationId = 4;
		int newFirestationId = 2;

		//WHEN
		ArgumentCaptor<Integer> argumentCaptorOldFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> argumentCaptorNewFirestationId = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		doReturn(true).when(this.safetyAlertsDAOFirestation).updateFirestationNumber( argumentCaptorOldFirestationId.capture(),
				argumentCaptorNewFirestationId.capture(), argumentCaptorAddress.capture());

		//THEN
		this.mockMvc.perform(put("/firestation")
				.param("oldFirestationId", "4")
				.param("newFirestationId", "2")
				.param("address", address))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorOldFirestationId.getValue().compareTo(oldFirestationId)==0);
		assert(argumentCaptorNewFirestationId.getValue().compareTo(newFirestationId)==0);
		Assert.assertEquals(argumentCaptorAddress.getValue(),address);
	}
	
	/**
	 * Test to verify @DeleteMapping /fireStation
	 * @throws Exception
	 */
	@Test
	void testShouldDeleteMappingOfAFirestation() throws Exception{

		//GIVEN
		Integer firestationId = 1;
		//WHEN
		ArgumentCaptor<Integer> argumentCaptorFirestationId = ArgumentCaptor.forClass(Integer.class);
		doReturn(true).when(this.safetyAlertsDAOFirestation).deleteMappingByFirestationId( argumentCaptorFirestationId.capture());

		//THEN
		this.mockMvc.perform(delete("/firestation").param("firestationId", "1"))
		.andExpect(status().isOk()).andReturn();
		assert(argumentCaptorFirestationId.getValue().compareTo(firestationId)==0);
	}

	/**
	 * Test to verify @DeleteMapping /fireStation
	 * @throws Exception
	 */
	@Test
	void testShouldDeleteMappingOfAnAddress() throws Exception{

		//GIVEN
		String address = "1509 Culver St";

		//WHEN
		ArgumentCaptor<String> argumentCaptorAddress = ArgumentCaptor.forClass(String.class);
		doReturn(true).when(this.safetyAlertsDAOFirestation).deleteMappingByAddress( argumentCaptorAddress.capture());

		//THEN
		this.mockMvc.perform(delete("/firestation").param("address", address))
		.andExpect(status().isOk()).andReturn();
		Assert.assertEquals(argumentCaptorAddress.getValue(),address);
	}
}