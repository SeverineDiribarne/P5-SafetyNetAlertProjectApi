package com.safetynetalert.alert.model;

import org.junit.jupiter.api.Test;

public class AddressTest {

	Address address = new Address();
	/**
	 * Test to add a firestationId
	 */
	@Test
	public void TestToAddAFirestationId() {
		//TODO Methode a rectifier nullPointerException : La liste fireStationIds est nulle
		
		//GIVEN
		int firestationId = 6;
		//WHEN
		address.addFirestationId(firestationId);
		//THEN
		assert(address.getFireStationIds().contains(6));
	}
	
	/**
	 * Test to remove a firestationId
	 * @param firestationId
	 */
	@Test
	public void TestToRemoveAFirestationId() {
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
	/**
	 * Test to update a firestationId
	 */
	@Test
	public void TestToUpdateAFirestationId() {
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
	/**
	 * Test to remove all firestationIds
	 */
	@Test
	public void TestToRemoveAllFirestationIds() {
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
	/**
	 * Test to verify if firestationIds contains a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsContainsFirestationId() {
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
	/**
	 * Test to verify if FirestationIds is equals to a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsEqualsFirestationId() {
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
}