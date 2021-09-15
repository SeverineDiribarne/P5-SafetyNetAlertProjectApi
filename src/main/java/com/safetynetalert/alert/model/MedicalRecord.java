package com.safetynetalert.alert.model;

import java.util.List;

public class MedicalRecord {
  private List<String> medication;
  
  private List<String> allergies;
  
  public List<String> getMedication() {
    return this.medication;
  }
  
  public void setMedication(List<String> medication) {
    this.medication = medication;
  }
  
  public List<String> getAllergies() {
    return this.allergies;
  }
  
  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }
  
  public String toString() {
    return "  Medications : " + getMedication() + 
      "  Allergies : " + getAllergies();
  }
}
