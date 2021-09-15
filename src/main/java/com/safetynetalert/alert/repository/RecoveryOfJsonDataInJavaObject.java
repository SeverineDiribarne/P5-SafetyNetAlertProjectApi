package com.safetynetalert.alert.repository;

import com.jsoniter.JsonIterator;
import com.safetynetalert.alert.model.Address;
import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.entities.FirestationsEntity;
import com.safetynetalert.alert.repository.entities.MedicalRecordsEntity;
import com.safetynetalert.alert.repository.entities.PersonsEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;

public class RecoveryOfJsonDataInJavaObject {
  private static RecoveryOfJsonDataInJavaObject repository;
  
  public static RecoveryOfJsonDataInJavaObject getInstance() {
    if (repository == null)
      repository = new RecoveryOfJsonDataInJavaObject(); 
    return repository;
  }
  
  private ArrayList<Person> persons = new ArrayList<>();
  
  private ArrayList<FireStation> firestations = new ArrayList<>();
  
  private ArrayList<MedicalRecord> medicalrecords = new ArrayList<>();
  
  public List<Person> getPersons() {
    return this.persons;
  }
  
  public List<FireStation> getFirestations() {
    return this.firestations;
  }
  
  public List<MedicalRecord> getMedicalrecords() {
    return this.medicalrecords;
  }
  
  public void initialize() throws IOException {
    File file = ResourceUtils.getFile("classpath:data.json");
    String content = Files.readString(file.toPath());
    DescriptionOfUncleanJavaObjects jsonDataInJavaObject = JsonIterator.deserialize(content, DescriptionOfUncleanJavaObjects.class);
    fillModelWithJsonDataObjects(jsonDataInJavaObject.getPersons(), jsonDataInJavaObject.getFirestations(), jsonDataInJavaObject.getMedicalrecords());
  }
  
  public void fillModelWithJsonDataObjects(List<PersonsEntity> personsEntities, List<FirestationsEntity> fireStationsEntities, List<MedicalRecordsEntity> medicalrecordsEntities) {
    for (FirestationsEntity fireStationEntity : fireStationsEntities) {
      FireStation fireStation = new FireStation();
      fireStation.setStationId(Integer.parseInt(fireStationEntity.getStation()));
      this.firestations.add(fireStation);
    } 
    int id = 0;
    for (PersonsEntity personEntity : personsEntities) {
      Person person = new Person();
      Address address = new Address();
      person.setFirstName(personEntity.getFirstName());
      person.setLastName(personEntity.getLastName());
      person.setEmail(personEntity.getEmail());
      person.setPhone(personEntity.getPhone());
      address.setStreet(personEntity.getAddress());
      address.setCity(personEntity.getCity());
      address.setZip(personEntity.getZip());
      person.setAddress(address);
      address.setFireStationIds(new ArrayList<>());
      for (FirestationsEntity fireStationEntity : fireStationsEntities) {
        if (person.getAddress().getStreet().equals(fireStationEntity.getAddress()))
          address.getFireStationIds().add(Integer.valueOf(Integer.parseInt(fireStationEntity.getStation()))); 
      } 
      person.setId(id++);
      this.persons.add(person);
    } 
    for (MedicalRecordsEntity medicalRecordEntity : medicalrecordsEntities) {
      for (Person person : this.persons) {
        if (person.getFirstName().compareTo(medicalRecordEntity.getFirstName()) == 0 && person.getLastName().compareTo(medicalRecordEntity.getLastName()) == 0) {
          person.setBirthDate(medicalRecordEntity.getBirthdate());
          MedicalRecord medicalRecord = new MedicalRecord();
          medicalRecord.setMedication(medicalRecordEntity.getMedications());
          medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
          person.setMedicalRecord(medicalRecord);
        } 
      } 
    } 
  }
}
