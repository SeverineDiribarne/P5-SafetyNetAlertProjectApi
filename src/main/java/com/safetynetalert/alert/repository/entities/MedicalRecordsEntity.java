package com.safetynetalert.alert.repository.entities;

import java.util.List;

public class MedicalRecordsEntity {
  private String firstName;
  
  private String lastName;
  
  private String birthdate;
  
  private List<String> medications;
  
  private List<String> allergies;
  
  public String getFirstName() {
    return this.firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return this.lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getBirthdate() {
    return this.birthdate;
  }
  
  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }
  
  public List<String> getMedications() {
    return this.medications;
  }
  
  public void setMedications(List<String> medications) {
    this.medications = medications;
  }
  
  public List<String> getAllergies() {
    return this.allergies;
  }
  
  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }
}
