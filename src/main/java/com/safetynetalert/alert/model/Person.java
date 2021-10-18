package com.safetynetalert.alert.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"age"})
public class Person {
	
	//Attributes 
	private int id;
	private String lastName;
	private String firstName;
	private Address address;
	private String phone;
	private String email;
	private String birthDate;
	private MedicalRecord medicalRecord;


	/**
	 * getter age
	 * @return this age
	 */
	@JsonProperty("age")
	public long getAge() {
		return ageCalculation();
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
	 * Age Calculation method
	 * @return age
	 */
	public long ageCalculation() {
		LocalDate actualDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		String birthday = getBirthDate();
		LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
		return ChronoUnit.YEARS.between(birthdayDate, actualDate);
	}
	
	/**
	 * ToString method
	 */
	public String toString() {
		return "  Id : " + getId() + 
				"  FirstName : " + getFirstName() + 
				"  LastName : " + getLastName() + 
				"  Address : " + getAddress().toString() +
				"  BirthDate : " + getBirthDate() + 
				"  Email : " + getEmail() + 
				"  Phone : " + getPhone();
	}
}
