package com.safetynetalert.alert.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.model.PersonsByStationId;
import com.safetynetalert.alert.service.SafetyAlertsDAO;

@RestController
public class AlertsController {

	//Attribute
	@Autowired
	private SafetyAlertsDAO safetyAlertsDao;

	/**
	 * GetAge method
	 * @param person
	 * @return age
	 */
	private long getAge(Person person) {
		LocalDate actualDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		String birthday = person.getBirthDate();
		LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
		return ChronoUnit.YEARS.between(birthdayDate, actualDate);
	}

	/**
	 * Get persons list covered by the firestation
	 * @param stationNumber
	 * @return persons list with lastname, firstname, address, phone number,
	 *  number of adults and number of children
	 */
	@GetMapping("/firestation")
	public PersonsByStationId getPersonsListCoveredByTheFireStation(@RequestParam Integer stationNumber) {
		List<Person> result = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChild = 0;
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().contains(stationNumber)) {
				long age = getAge(person);
				person.setAge(age);
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
		return personsByStationId;
	}

	/**
	 * Get the list of children living at the address
	 * @param address
	 * @return children list with firstname, lastname, age and other members of family 
	 * or empty list if no children at the requested address
	 */
	@GetMapping({"/childAlert"})
	public List<Person> getTheListOfChildrenLivingAtTheAddress(@RequestParam String address) {
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			long age = getAge(person);
			person.setAge(age);
			if (person.getAddress().getStreet().contains(address) && (age <= 18) ) {
				result.add(person);  
			}
		}
		return result;
	}

	/**
	 * Get the phone number list of people covered by the firestation
	 * @param firestation
	 * @return list of phone number from everyone covered by the firestation
	 */
	@GetMapping({"/phoneAlert"})
	public List<String> getThePhoneNumberListOfPeopleCoveredByTheFireStation
	(@RequestParam Integer firestation) {
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().contains(firestation))
				result.add(person.getPhone()); 
		} 
		return result;
	}

	/**
	 * Get the list of people living at the address as well as the firestation serving it
	 * @param address
	 * @return list of people living at the address as well as the firestation serving it
	 */
	@GetMapping({"/fire"})
	public List<Person> getTheListOfPeopleLivingAtTheAddressAsWellAsTheFirestationServingIt
	(@RequestParam String address) {
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getStreet().contains(address))
				result.add(person); 
		} 
		return result;
	}

	/**
	 * Get the list of homes served by the Firestation
	 * @param stations
	 * @return list of homes served by the firestation
	 */
	@GetMapping({"/flood/stations"})
	public List<Person> getTheListOfHomesServedByTheFirestation(@RequestParam List<Integer> stations) {
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().equals(stations))
				result.add(person); 
		} 
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
		Person result = null;
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
				result = person; 
		} 
		return result;
	}
	/**
	 * Get the email list of all the inhabitants of the city
	 * @param city
	 * @return list of emails from everyone in town
	 */
	@GetMapping({"/communityEmail"})
	public List<String> getTheEmailListOfAllTheInhabitantsOfTheCity(@RequestParam String city) {
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons())
			result.add(person.getEmail()); 
		return result;
	}

	//-------------------------------------"/person"------------------------------------------
	
	/**
	 * Create a new person
	 * @param person
	 * @return person added
	 */
	@PostMapping(value = "/person")
	public Person createNewPerson(@RequestBody Person person) {
		return safetyAlertsDao.addPersons(person);
	}

	/**
	 * Update a person with his firstName and lastName
	 * @param person
	 */
	@PutMapping("/person")
	public void updatePerson(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName, @RequestBody Person person) {
			safetyAlertsDao.updatePerson(firstName, lastName, person);
	}

	/**
	 * Delete a person
	 * @param person
	 */
	@DeleteMapping ("/person")
	public void deletePerson(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName) {
		safetyAlertsDao.deletePerson(firstName, lastName);
	}
	
	//------------------------------------"/firestation"----------------------------------------
	
	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	@PostMapping("/firestation")
	public void addAMappingFireStationIdWithAnAddress(@RequestParam Integer firestationId, @RequestParam String address ) {
		safetyAlertsDao.addMapping(firestationId, address);
	}
	
	/**
	 * Update the fireStation number based on an address
	 * @param firestation
	 */
	@PutMapping("/firestation")
	public void updateTheFireStationNumberBasedOnAnAddress(@RequestParam Integer firestationId, @RequestParam String address) {
		safetyAlertsDao.updateFirestationNumber(firestationId, address);
		
	}
	
	/**
	 * Delete the mapping of a fireStation or an address
	 */
	@DeleteMapping("/firestation")
	public void deleteTheMappingOfAFireStationOrAnAddress(@RequestParam Integer firestationId, @RequestParam String address) {
		safetyAlertsDao.deleteMapping(firestationId, address);
	}
	
	//---------------------------------"/medicalRecord"---------------------------------------
	
	/**
	 * Add a medicalRecord
	 * @param medicalRecord
	 * @return medicalRecord
	 */
	@PostMapping("/medicalRecord")
	public MedicalRecord addAMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return safetyAlertsDao.addMedicalRecord(medicalRecord);
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
		safetyAlertsDao.updateMedicalRecord(firstName, lastName, person);
	}
	
	/**
	 * delete a person's medical record
	 * @param firstName
	 * @param lastName
	 */
	@DeleteMapping("/medicalRecord")
	public void deleteAPersonSMedicalRecord(@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName) {
		safetyAlertsDao.deleteMedicalRecord(firstName, lastName);
	}
}
