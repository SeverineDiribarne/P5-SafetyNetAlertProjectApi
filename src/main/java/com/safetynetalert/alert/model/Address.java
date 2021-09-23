package com.safetynetalert.alert.model;

import java.util.List;

public class Address {
	
	//Attributes
	private String street;
	private String city;
	private String zip;
	private List<Integer> fireStationIds;

	/**
	 * getter street
	 * @return this street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Setter street
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * getter city
	 * @return this city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Setter city
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * getter zip
	 * @return this zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Setter zip code
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * getter fireStationIds
	 * @return this fireStationIds
	 */
	public List<Integer> getFireStationIds() {
		return this.fireStationIds;
	}

	/**
	 * Setter FireStationIds
	 * @param fireStationIds
	 */
	public void setFireStationIds(List<Integer> fireStationIds) {
		this.fireStationIds = fireStationIds;
	}
}
