package com.safetynetalert.alert.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
	private SafetyAlertsDAO safetyAlertsDao;

	private long getAge(Person person) {
		LocalDate actualDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		String birthday = person.getBirthDate();
		LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
		return ChronoUnit.YEARS.between(birthdayDate, actualDate);
	}

	@GetMapping({"/firestation"})
	public PersonsByStationId getPersonsListCoveredByTheFireStation(@RequestParam Integer stationNumber) {
		List<Person> result = new ArrayList<>();
		int numberOfAdults = 0;
		int numberOfChild = 0;
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().contains(stationNumber)) {
				long age = getAge(person);
				person.setAge(age);
				result.add(person);
				if (age > 18L) {
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

	@GetMapping({"/phoneAlert"})
	public List<String> getThePhoneNumberListOfPeopleCoveredByTheFireStation(@RequestParam Integer firestation) {
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().contains(firestation))
				result.add(person.getPhone()); 
		} 
		return result;
	}

	@GetMapping({"/fire"})
	public List<Person> getTheListOfPeopleLivingAtTheAddressAsWellAsTheFirestationServingIt(@RequestParam String address) {
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getStreet().contains(address))
				result.add(person); 
		} 
		return result;
	}

	@GetMapping({"/flood/stations"})
	public List<Person> getTheListOfHomesServedByTheFirestation(@RequestParam List<Integer> stations) {
		List<Person> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getAddress().getFireStationIds().equals(stations))
				result.add(person); 
		} 
		return result;
	}

	@GetMapping({"/personInfo"})
	public Person getTheListOfEachInhabitantsSortedByName(@RequestParam String firstName, @RequestParam String lastName) {
		Person result = null;
		for (Person person : this.safetyAlertsDao.getPersons()) {
			if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName))
				result = person; 
		} 
		return result;
	}

	@GetMapping({"/communityEmail"})
	public List<String> getTheEmailListOfAllTheInhabitantsOfTheCity(@RequestParam String city) {
		List<String> result = new ArrayList<>();
		for (Person person : this.safetyAlertsDao.getPersons())
			result.add(person.getEmail()); 
		return result;
	}
}
