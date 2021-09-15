package com.safetynetalert.alert.model;

public class Person {
  private int id;
  
  private String lastName;
  private String firstName;
  private Address address;
  private String phone;
  private String email;
  private String birthDate;
  private long age;
  private MedicalRecord medicalRecord;
  
  
  public long getAge() {
    return this.age;
  }
  
  public void setAge(long age) {
    this.age = age;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getLastName() {
    return this.lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getFirstName() {
    return this.firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public Address getAddress() {
    return this.address;
  }
  
  public void setAddress(Address address) {
    this.address = address;
  }
  
  public String getPhone() {
    return this.phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getBirthDate() {
    return this.birthDate;
  }
  
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
  
  public MedicalRecord getMedicalRecord() {
    return this.medicalRecord;
  }
  
  public void setMedicalRecord(MedicalRecord medicalRecord) {
    this.medicalRecord = medicalRecord;
  }
  
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
