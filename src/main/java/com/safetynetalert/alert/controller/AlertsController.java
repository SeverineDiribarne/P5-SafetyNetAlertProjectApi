package com.safetynetalert.alert.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.model.PersonsByStationId;
import com.safetynetalert.alert.service.SafetyAlertsDAO;

@RestController
public class AlertsController {

	@Autowired
	public SafetyAlertsDAO safetyAlertsDao;

	private static final Logger log = LogManager.getLogger(); 
	private static final String REPLY = "Reply sent : {}";

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
		log.info(REPLY, personsByStationId);
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
		for (Person person : safetyAlertsDao.getPersons()) {
			long age = person.ageCalculation();
			if (person.getAddress().getStreet().contains(address) && (age <= 18) ) {
				result.add(person);  
			}
		}
		log.info(REPLY, result);
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
		for (Person person : safetyAlertsDao.getPersons()) {
			if (person.getAddress().containsFirestationId(firestation))
				result.add(person.getPhone()); 
		} 
		log.info(REPLY, result);
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
		for (Person person : safetyAlertsDao.getPersons()) {
			if (person.getAddress().getStreet().contains(address))
				result.add(person); 
		} 
		log.info(REPLY, result);
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
		for (Person person : safetyAlertsDao.getPersons()) {
			if (person.getAddress().equalsFirestationId(stations)) {
				result.add(person); 
			}
		} 
		log.info(REPLY, result);
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
		for (Person person : safetyAlertsDao.getPersons()) {
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
				result = person; 
		} 
		log.info(REPLY, result);
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
		for (Person person : safetyAlertsDao.getPersons()) 
		{
			result.add(person.getEmail()); 
		}
		log.info(REPLY, result);
		return result;
	}
}
