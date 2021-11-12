package com.safetyNetAlert.Alert.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.safetynetalert.alert.model.Address;

class AddressTest {

	/**
	 * Creation New Address
	 * @return address
	 */
	private Address creationNewAddress() {
		Address address = new Address();
		return address;
	}

	/**
	 * Test to add a firestationId
	 */
	@Test
	void TestToAddAFirestationId() {

		//GIVEN
		Address address = creationNewAddress();
		Integer firestationId = 6;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.addFirestationId(firestationId);
		
		//THEN
		Assert.assertEquals(address.getFireStationIds().get(2),firestationId);
	}

	/**
	 * Test to remove a firestationId
	 * @param firestationId
	 */
	@Test
	void TestToRemoveAFirestationId() {

		//GIVEN
		Address address = creationNewAddress();
		int firestationId = 1;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.removeFirestationId(firestationId);

		//THEN
		Assert.assertTrue(!address.getFireStationIds().contains(firestationId));
	}

	/**
	 * Test to update a firestationId
	 */
	@Test
	void TestToUpdateAFirestationId() {

		//GIVEN
		Address address = creationNewAddress();
		int oldFirestationId = 2;
		Integer newFirestationId = 6;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.updateFirestationId(oldFirestationId, newFirestationId);
		
		//THEN
		Assert.assertEquals(newFirestationId,address.getFireStationIds().get(1));

	}

	/**
	 * Test to remove all firestationIds
	 */
	@Test
	void TestToRemoveAllFirestationIds() {

		//GIVEN
		Address address = creationNewAddress();
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.removeAllFirestationIds();

		//THEN
		Assert.assertTrue(address.getFireStationIds().isEmpty());
	}

	/**
	 * Test to verify if firestationIds contains a firestationId
	 */
	@Test
	void TestToVerifyIfFirestationIdsContainsFirestationId() {
		
		//GIVEN
		Address address = creationNewAddress();
		Integer firestationId = 2;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(firestationId);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.containsFirestationId(firestationId);

		//THEN
		Assert.assertTrue(address.getFireStationIds().contains(firestationId));
	}

	/**
	 * Test to verify if FirestationIds is equals to a firestationId
	 */
	@Test
	void TestToVerifyIfFirestationIdsEqualsFirestationId() {

		//GIVEN
				Address address = creationNewAddress();
				
				List<Integer>addToTheList = new ArrayList<Integer>();
				addToTheList.add(1);
				addToTheList.add(2);
				address.setFireStationIds(addToTheList);

				//WHEN
				address.equalsFirestationId(addToTheList);

				//THEN
				Assert.assertEquals(address.getFireStationIds(), addToTheList);
	}
}