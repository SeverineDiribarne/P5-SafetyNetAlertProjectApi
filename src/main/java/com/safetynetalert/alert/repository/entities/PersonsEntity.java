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
	 * Getter lastName
	 * @return this lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Getter address 
	 * @return this address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Getter city
	 * @return this city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Getter zip
	 * @return this zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Getter phone
	 * @return this phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Getter email
	 * @return this email
	 */
	public String getEmail() {
		return this.email;
	}
}
