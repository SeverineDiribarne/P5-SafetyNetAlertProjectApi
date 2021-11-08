package com.safetynetalert.alert.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.model.PersonsByStationId;
import com.safetynetalert.alert.service.SafetyAlertsDAO;

@RestController
public class AlertsController {

	//Attribute
	@Autowired
	private SafetyAlertsDAO safetyAlertsDao;
	
	private static final Logger log = LogManager.getLogger(); 
	private static final String reply = "Reply sent : {}";

	/**
	 * Get persons list covered by the fireStation
	 * @param stationNumber
	 * @return persons list with lastName, firstName, address, phone number,
	 *  number of adults and number of children
	 */
	@GetMapping("/firestation")
	public PersonsByStationId getPersonsListCoveredByTheFireStation(@RequestParam Integer stationNumber) {
		log.info("Request received with input parameter: stationNumber {}", stationNumber);

		List<Person> result = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChild = 0;
		for (Person person : safetyAlertsDao.getPersons()) {
			if (person.getAddress().containsFirestationId(stationNumber)) {
				long age = person.ageCalculation();
				result.add(person);
				if (age > 18) {
					numberOfAdults++;
					continue;
				} 
				numberOfChild++;
			} 
		} 
		PersonsByStationId personsByStationId = new PersonsByStationId();
		personsByStationId.setPersons(result);
		personsByStationId.setNumberOfAdults(numberOfAdults);
		personsByStationId.setNumberOfChild(numberOfChild);
		log.info(reply, personsByStationId);
		return personsByStationId;
	}

	/**
	 * Get the list of children living at the address
	 * @param address
	 * @return children list with firstName, lastName, age and other members of family 
	 * or empty list if no children at the requested address
	 */
	@GetMapping({"/childAlert"})
	public List<Person> getTheListOfChildrenLivingAtTheAddress(@RequestParam String address) {
		log.info("Request received with input parameter: address {}", address);

		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			long age = person.ageCalculation();
			if (person.getAddress().getStreet().contains(address) && (age <= 18) ) {
				result.add(person);  
			}
		}
		log.info(reply, result);
		return result;
	}

	/**
	 * Get the phone number list of people covered by the fireStation
	 * @param firestation
	 * @return list of phone number from everyone covered by the fireStation
	 */
	@GetMapping({"/phoneAlert"})
	public List<String> getThePhoneNumberListOfPeopleCoveredByTheFireStation
	(@RequestParam Integer firestation) {
		log.info("Request received with input parameter: firestation {}", firestation);
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().containsFirestationId(firestation))
				result.add(person.getPhone()); 
		} 
		log.info(reply, result);
		return result;
	}

	/**
	 * Get the list of people living at the address as well as the fireStation serving it
	 * @param address
	 * @return list of people living at the address as well as the fireStation serving it
	 */
	@GetMapping({"/fire"})
	public List<Person> getTheListOfPeopleLivingAtTheAddressAsWellAsTheFirestationServingIt
	(@RequestParam String address) {
		log.info("Request received with input parameter: address {}", address);
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getStreet().contains(address))
				result.add(person); 
		} 
		log.info(reply, result);
		return result;
	}

	/**
	 * Get the list of homes served by the fireStation
	 * @param stations
	 * @return list of homes served by the fireStation
	 */
	@GetMapping({"/flood/stations"})
	public List<Person> getTheListOfHomesServedByTheFirestation(@RequestParam List<Integer> stations) {
		log.info("Request received with input parameter: stations {}", stations);
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().equalsFirestationId(stations)) {
				result.add(person); 
			}
		} 
		log.info(reply, result);
		return result;
	}

	/**
	 * Get the list of each inhabitants sorted by name
	 * @param firstName
	 * @param lastName
	 * @return list of each inhabitants sorted by name
	 */
	@GetMapping({"/personInfo"})
	public Person getTheListOfEachInhabitantsSortedByName(@RequestParam String firstName,
			@RequestParam String lastName) {
		log.info("Request received with input parameters: firstName {}, lastName {}", firstName , lastName);
		Person result = null;
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
				result = person; 
		} 
		log.info(reply, result);
		return result;
	}
	/**
	 * Get the email list of all the inhabitants of the city
	 * @param city
	 * @return list of Email from everyone in town
	 */
	@GetMapping({"/communityEmail"})
	public List<String> getTheEmailListOfAllTheInhabitantsOfTheCity(@RequestParam String city) {
		log.info("Request received with input parameter: city {}", city );
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) 
		{
			result.add(person.getEmail()); 
		}
		log.info(reply, result);
		return result;
	}

	//-------------------------------------"/person"------------------------------------------

	/**
	 * Create a new person
	 * @param person
	 * @return person added
	 */
	@PostMapping(value = "/person")
	public void createNewPerson(@RequestBody Person person) {
		log.info("Request to add a person with request body: person {}", person );
		boolean result = safetyAlertsDao.addPersons(person);
		if( result )
		{
			log.info("Answer sent: The person has been added.");
		}
		else
		{
			log.info("Answer sent: The person could not be added.");
		}
	}

	/**
	 * Update a person with his firstName and lastName
	 * @param person
	 */
	@PutMapping("/person")
	public void updatePerson(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName, @RequestBody Person person) {
		log.info("Person modification request with input parameters: firstName {}, lastName {}, and request body: person {}", firstName, lastName, person );
		boolean result = safetyAlertsDao.updatePerson(firstName, lastName, person);
		if( result )
		{
			log.info("Answer sent: The person has been updated by firstName and lastName.");
		}
		else
		{
			log.info("Answer sent: The person could not be updated by firstName and lastName.");
		}
	}

	/**
	 * Delete a person
	 * @param person
	 */
	@DeleteMapping ("/person")
	public void deletePerson(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName) {
		log.info("Request to delete a person with input parameters: firstName {}, lastName {}", firstName, lastName);
		boolean result = safetyAlertsDao.deletePerson(firstName, lastName);
		if( result )
		{
			log.info("Answer sent: The person has been deleted by firstName and lastName.");
		}
		else
		{
			log.info("Answer sent: The person could not be deleted by firstName and lastName.");
		}
	}

	//------------------------------------"/firestation"----------------------------------------

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	@PostMapping("/firestation")
	public void addAMappingFireStationIdWithAnAddress(@RequestParam Integer firestationId, @RequestParam String address ) {
		log.info("Request to add a mapping with input parameters: firestationId {}, address {}", firestationId, address);
		boolean result = safetyAlertsDao.addMapping(firestationId, address);
		if( result )
		{
			log.info("Answer sent: The mapping has been added.");
		}
		else
		{
			log.info("Answer sent: The mapping could not be added.");
		}
	}

	/**
	 * Update the fireStation number based on an address
	 * @param firestation
	 */
	@PutMapping("/firestation")
	public void updateTheFireStationNumberBasedOnAnAddress(@RequestParam Integer oldFirestationId, @RequestParam Integer newFirestationId, @RequestParam String address) {
		log.info("Request to modify a mapping with input parameters: oldFirestationId {}, newFirestationId {}, address {}", oldFirestationId, newFirestationId, address);
		boolean result = safetyAlertsDao.updateFirestationNumber(oldFirestationId, newFirestationId, address);
		if( result )
		{
			log.info("Answer sent: The fireStationId has been updated.");
		}
		else
		{
			log.info("Answer sent: The fireStationId could not be updated.");
		}
	}

	/**
	 * Delete the mapping by fireStationId or by address
	 * @param firestationId
	 * @param address
	 */
	@DeleteMapping("/firestation")
	public void deleteTheMappingByFirestationIdOrByAddress(@RequestParam(required = false) Integer firestationId, @RequestParam(required = false) String address) {
		log.info("Request to delete a mapping with parameters (not required) as input: firestationId {}, address {}", firestationId, address);
		//Delete the mapping by fireStationId
		if(firestationId != null) {
			boolean result = safetyAlertsDao.deleteMappingByFirestationId(firestationId);
			if( result )
			{
				log.info("Answer sent: The mapping has been deleted by firestationId.");
			}
			else
			{
				log.info("Answer sent: The medical file could not be deleted by firestationId.");
			}
		}
		//Delete the mapping by address
		if(address != null) {
			boolean result = safetyAlertsDao.deleteMappingByAddress(address);
			if( result )
			{
				log.info("Answer sent: The mapping has been deleted by address.");
			}
			else
			{
				log.info("Answer sent: The mapping could not be deleted by address.");
			}
		}
	}

	//---------------------------------"/medicalRecord"---------------------------------------

	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return medicalRecord
	 */
	@PostMapping("/medicalRecord")
	public void addAMedicalRecord(@RequestParam int personId, @RequestBody MedicalRecord medicalRecord ) {
		log.info("Request to add a medical record to a person with input parameter: personId {}, and request body: medicalRecord {}", personId, medicalRecord);
		boolean result = safetyAlertsDao.addMedicalRecord(personId, medicalRecord );
		if( result )
		{
			log.info("Answer sent: The medical file has been added to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be added to the requested person.");
		}
	}
	/**
	 * Update an existing MedicalRecord of a specific person 
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	@PutMapping("/medicalRecord")
	public void updateAnExistingMedicalRecord(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,@RequestBody Person person) {
		log.info("Change request with input parameters: firstName {}, lastName {}, and request body: person {}", firstName, lastName, person);
		boolean result = safetyAlertsDao.updateMedicalRecord(firstName, lastName, person);
		if( result )
		{
			log.info("Answer sent: The medical file has been updated to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be updated to the requested person.");
		}
	}

	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	@DeleteMapping("/medicalRecord")
	public void deleteAPersonSMedicalRecord(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName) {
		log.info("Delete request with input parameters: firstName {}, lastName {}", firstName, lastName);
		boolean result = safetyAlertsDao.deleteMedicalRecord(firstName, lastName);
		if( result )
		{
			log.info("Answer sent: The medical file has been deleted to the requested person.");
		}
		else
		{
			log.info("Answer sent: The medical file could not be deleted to the requested person.");
		}
	}
}
