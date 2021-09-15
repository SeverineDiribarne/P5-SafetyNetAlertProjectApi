package com.safetynetalert.alert.repository;

import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;
import java.util.List;

public class DescriptionOfUncleanJavaObjects {
  private List<PersonsEntity> persons;
  
  private List<FirestationsEntity> firestations;
  
  private List<MedicalRecordsEntity> medicalrecords;
  
  public List<PersonsEntity> getPersons() {
    return this.persons;
  }
  
  public void setPersonsEntities(List<PersonsEntity> persons) {
    this.persons = persons;
  }
  
  public List<FirestationsEntity> getFirestations() {
    return this.firestations;
  }
  
  public void setFireStationsEntities(List<FirestationsEntity> firestations) {
    this.firestations = firestations;
  }
  
  public List<MedicalRecordsEntity> getMedicalrecords() {
    return this.medicalrecords;
  }
  
  public void setMedicalrecordsEntities(List<MedicalRecordsEntity> medicalrecords) {
    this.medicalrecords = medicalrecords;
  }
}
