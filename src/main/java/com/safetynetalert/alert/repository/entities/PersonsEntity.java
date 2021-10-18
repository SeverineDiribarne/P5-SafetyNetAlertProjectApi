package com.safetynetalert.alert.repository.entities;

public class PersonsEntity {
	
	//Attributes
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;

	/**
	 * Getter firstName
	 * @return this firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setter firstName
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter lastName
	 * @return this lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setter lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter address 
	 * @return this address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Setter address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter city
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
	 * Getter zip
	 * @return this zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Setter zip
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Getter phone
	 * @return this phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Setter phone
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter email
	 * @return this email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Setter email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
