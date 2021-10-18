package com.safetynetalert.alert.model;

import java.util.List;

public class PersonsByStationId {
	
	// Attributes
	private List<Person> persons;
	private int numberOfAdults;
	private int numberOfChild;

	/**
	 * Getter persons
	 * @return this persons
	 */
	public List<Person> getPersons() {
		return this.persons;
	}

	/**
	 * Setter persons
	 * @param persons
	 */
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	/**
	 * Getter numberOfAdults
	 * @return this numberOfAdults
	 */
	public int getNumberOfAdults() {
		return this.numberOfAdults;
	}

	/**
	 * Setter numberOfAdults
	 * @param numberOfAdults
	 */
	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	/**
	 * Getter numberOfChild
	 * @return this numberOfChild
	 */
	public int getNumberOfChild() {
		return this.numberOfChild;
	}

	/**
	 * Setter numberOfChild
	 * @param numberOfChild
	 */
	public void setNumberOfChild(int numberOfChild) {
		this.numberOfChild = numberOfChild;
	}
}
