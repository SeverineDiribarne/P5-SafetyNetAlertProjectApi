package com.safetynetalert.alert.model;

public class Person {
	
	//Attributes 
	private int id;
	private String lastName;
	private String firstName;
	private Address address;
	private String phone;
	private String email;
	private String birthDate;
	private long age;
	private MedicalRecord medicalRecord;


	/**
	 * getter age
	 * @return this age
	 */
	public long getAge() {
		return this.age;
	}

	/**
	 * Setter age
	 * @param age
	 */
	public void setAge(long age) {
		this.age = age;
	}

	/**
	 * Getter id
	 * @return this id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
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
	 * getter firstName
	 * @return this fisrtName
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
	 * Getter Address
	 * @return this address
	 */
	public Address getAddress() {
		return this.address;
	}

	/**
	 * Setter Address
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
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

	/**
	 * Getter birthDate
	 * @return this birthDate
	 */
	public String getBirthDate() {
		return this.birthDate;
	}

	/**
	 * Setter birthDate
	 * @param birthDate
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * getter medicalRecord
	 * @return this medicalRecord
	 */
	public MedicalRecord getMedicalRecord() {
		return this.medicalRecord;
	}

	/**
	 * Setter medicalRecord
	 * @param medicalRecord
	 */
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	/**
	 * ToString method
	 */
	public String toString() {
		return "  Id : " + getId() + 
				"  FirstName : " + getFirstName() + 
				"  LastName : " + getLastName() + 
				"  Address : " + getAddress().getStreet() + 
				"  City : " + getAddress().getCity() + 
				"  Zip Code : " + getAddress().getZip() + 
				"  BirthDate : " + getBirthDate() + 
				"  Email : " + getEmail() + 
				"  Phone : " + getPhone();
	}
}
