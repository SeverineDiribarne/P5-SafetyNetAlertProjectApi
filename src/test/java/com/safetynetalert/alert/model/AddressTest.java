package com.safetynetalert.alert.model;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> feature/Tests
import org.junit.jupiter.api.Test;

public class AddressTest {

<<<<<<< HEAD
	Address address = new Address();
=======
	/**
	 * Creation New Address
	 * @return address
	 */
	private Address creationNewAddress() {
		Address address = new Address();
		return address;
	}

>>>>>>> feature/Tests
	/**
	 * Test to add a firestationId
	 */
	@Test
	public void TestToAddAFirestationId() {
<<<<<<< HEAD
		//TODO Methode a rectifier nullPointerException : La liste fireStationIds est nulle
		
		//GIVEN
		int firestationId = 6;
		//WHEN
		address.addFirestationId(firestationId);
		//THEN
		assert(address.getFireStationIds().contains(6));
	}
	
=======

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

>>>>>>> feature/Tests
	/**
	 * Test to remove a firestationId
	 * @param firestationId
	 */
	@Test
	public void TestToRemoveAFirestationId() {
<<<<<<< HEAD
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
=======

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

>>>>>>> feature/Tests
	/**
	 * Test to update a firestationId
	 */
	@Test
	public void TestToUpdateAFirestationId() {
<<<<<<< HEAD
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
=======

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

>>>>>>> feature/Tests
	/**
	 * Test to remove all firestationIds
	 */
	@Test
	public void TestToRemoveAllFirestationIds() {
<<<<<<< HEAD
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
=======

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

>>>>>>> feature/Tests
	/**
	 * Test to verify if firestationIds contains a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsContainsFirestationId() {
<<<<<<< HEAD
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
	}
	
=======
		
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

>>>>>>> feature/Tests
	/**
	 * Test to verify if FirestationIds is equals to a firestationId
	 */
	@Test
	public void TestToVerifyIfFirestationIdsEqualsFirestationId() {
<<<<<<< HEAD
		//TODO Method to complete

		//GIVEN
		
		//WHEN
		
		//THEN
		
=======

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
>>>>>>> feature/Tests
	}
}