package com.safetynetalert.alert.service;

import com.safetynetalert.alert.model.Person;

public interface SafetyAlertsDAOPerson {

	/**
	 * add persons
	 * @param person
	 * @return person added
	 */
	boolean addPersons(Person person);

	/**
	 * Update person
	 * @param firstName
	 * @param lastName
	 * @param person
	 */
	boolean updatePerson(String firstName, String lastName, Person person);

	/**
	 * Delete a person
	 * @param firstName
	 * @param lastName
	 */
	boolean deletePerson(String firstName, String lastName);

}