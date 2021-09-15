package com.safetynetalert.alert.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.model.FireStation;
import com.safetynetalert.alert.model.MedicalRecord;
import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;

@Service
public class SafetyAlertsDAO {
  public List<Person> getPersons() {
    return RecoveryOfJsonDataInJavaObject.getInstance().getPersons();
  }
  
  public List<FireStation> getFireStations() {
    return RecoveryOfJsonDataInJavaObject.getInstance().getFirestations();
  }
  
  public List<MedicalRecord> getMedicalRecords() {
    return RecoveryOfJsonDataInJavaObject.getInstance().getMedicalrecords();
  }
}
