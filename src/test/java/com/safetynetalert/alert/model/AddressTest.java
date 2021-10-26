package com.safetynetalert.alert.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AddressTest {

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
	public void TestToAddAFirestationId() {

		//GIVEN
		Address address = creationNewAddress();
		int firestationId = 6;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.addFirestationId(firestationId);
		
		//THEN
		assert(address.getFireStationIds().get(2).compareTo(6) == 0);
	}

	/**
	 * Test to remove a firestationId
	 * @param firestationId
	 */
	@Test
	public void TestToRemoveAFirestationId() {

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
		assert(!address.getFireStationIds().contains(firestationId));
	}

	/**
	 * Test to update a firestationId
	 */
	@Test
	public void TestToUpdateAFirestationId() {

		//GIVEN
		Address address = creationNewAddress();
		int oldFirestationId = 2;
		int newFirestationId = 6;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.updateFirestationId(oldFirestationId, newFirestationId);
		
		//THEN
		assert(address.getFireStationIds().get(1).compareTo(6) == 0);

	}

	/**
	 * Test to remove all firestationIds
	 */
	@Test
	public void TestToRemoveAllFirestationIds() {

		//GIVEN
		Address address = creationNewAddress();
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(2);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.removeAllFirestationIds();

		//THEN
		assert(address.getFireStationIds().isEmpty());
	}

	/**
	 * Test to verify if firestationIds contains a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsContainsFirestationId() {
		
		//GIVEN
		Address address = creationNewAddress();
		int firestationId = 2;
		List<Integer>addToTheList = new ArrayList<Integer>();
		addToTheList.add(1);
		addToTheList.add(firestationId);
		address.setFireStationIds(addToTheList);

		//WHEN
		address.containsFirestationId(firestationId);

		//THEN
		assert(address.getFireStationIds().contains(2));
	}

	/**
	 * Test to verify if FirestationIds is equals to a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsEqualsFirestationId() {

		//GIVEN
				Address address = creationNewAddress();
				
				List<Integer>addToTheList = new ArrayList<Integer>();
				addToTheList.add(1);
				addToTheList.add(2);
				address.setFireStationIds(addToTheList);

				//WHEN
				address.equalsFirestationId(addToTheList);

				//THEN
				assert(address.getFireStationIds().equals(addToTheList));
	}
}