package com.safetynetalert.alert.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynetalert.alert.model.Person;

public class RepositoryFirestation {

	RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();

	private static final Logger log = LogManager.getLogger(); 
	private static final String FOUND = "person found";
	private static final String NOTFOUND = "person not found";

	/**
	 * Get person by address
	 * @param address
	 * @return
	 */
	private List<Person> getPersonByAddress(String address) {
		List<Person> personsFound = new ArrayList<>();
		for (Person person : repository.getPersons()) {
			if (person.getAddress().getStreet().equals(address)) {
				log.debug(FOUND);
				personsFound.add(person);
			}
		}
		if(personsFound.isEmpty()) {
			log.debug(NOTFOUND);
		}
		return personsFound;
	}

	/**
	 * get person by firestationId
	 * @param firestationId
	 * @return
	 */
	private List<Person> getPersonByFirestationId(Integer firestationId) {
		List<Person> personsFound = new ArrayList<>();
		for (Person person : repository.getPersons()) {
			if(person.getAddress().containsFirestationId(firestationId)) {
				log.debug(FOUND);
				personsFound.add(person);
			}
		}
		if(personsFound.isEmpty()) {
			log.debug(NOTFOUND);
		}
		return personsFound;
	}

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	public boolean addMapping(Integer firestationId, String address) {

		List<Person> personsFound = getPersonByAddress(address);
		if(personsFound.isEmpty()) {
			log.debug("Return false : the mapping isn't added for each person found");
			return false;
		}
		for(Person person : personsFound) {
			person.getAddress().addFirestationId(firestationId);
		}
		log.debug("Return true : the mapping is added for each person found");
		return true;
	}

	/**
	 * Update the FirestationNumber based on an address
	 * @param firestationId
	 * @param address
	 */
	public boolean updateFirestationNumber(Integer oldfirestationId, Integer newfirestationId, String address) {
		List<Person> personsFound = getPersonByAddress(address);
		if(personsFound.isEmpty()) {
			log.debug("Return false : firestationId isn't updated for each person found");
			return false;
		}
		for(Person person : personsFound) {
			person.getAddress().updateFirestationId(oldfirestationId, newfirestationId);
		}
		log.debug("Return true : the firestationId is updated for each person found");
		return true;
	}

	/**
	 * Delete mapping by FirestationId
	 * @param firestationId
	 * @param address
	 */
	public boolean deleteMappingByFirestationId(Integer firestationId) {
		//Delete the mapping by fireStationId
		if(firestationId != null) {
			List<Person> personsFound = getPersonByFirestationId(firestationId);
			for(Person person : personsFound) {
				person.getAddress().removeFirestationId(firestationId);
			}
			log.debug("Return true : the mapping is cleared for each person found");
			return true;
		}
		log.debug("Return false : the mapping isn't cleared for each person found");
		return false;
	}

	/**
	 * Delete the mapping by address
	 * @param address
	 */
	public boolean deleteMappingByAddress(String address) {
		//Delete the mapping by address
		if(address != null) {
			List<Person> personsFound=getPersonByAddress(address);
			for(Person person : personsFound) {
				person.getAddress().removeAllFirestationIds();
			}
			log.debug("Return true : the mapping is cleared for each person found");
			return true;
		}
		log.debug("Return false : the mapping isn't cleared for each person found");
		return false;	
	}
}
