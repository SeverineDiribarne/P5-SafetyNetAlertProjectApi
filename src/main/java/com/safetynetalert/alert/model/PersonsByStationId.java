package com.safetynetalert.alert.model;

import java.util.List;

public class PersonsByStationId {
  private List<Person> persons;
  
  private int numberOfAdults;
  
  private int numberOfChild;
  
  public List<Person> getPersons() {
    return this.persons;
  }
  
  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }
  
  public int getNumberOfAdults() {
    return this.numberOfAdults;
  }
  
  public void setNumberOfAdults(int numberOfAdults) {
    this.numberOfAdults = numberOfAdults;
  }
  
  public int getNumberOfChild() {
    return this.numberOfChild;
  }
  
  public void setNumberOfChild(int numberOfChild) {
    this.numberOfChild = numberOfChild;
  }
}
