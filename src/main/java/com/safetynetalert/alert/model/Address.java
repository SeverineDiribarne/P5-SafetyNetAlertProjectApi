package com.safetynetalert.alert.model;

import java.util.List;

public class Address {
  private String street;
  
  private String city;
  
  private String zip;
  
  private List<Integer> fireStationIds;
  
  public String getStreet() {
    return this.street;
  }
  
  public void setStreet(String street) {
    this.street = street;
  }
  
  public String getCity() {
    return this.city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  public String getZip() {
    return this.zip;
  }
  
  public void setZip(String zip) {
    this.zip = zip;
  }
  
  public List<Integer> getFireStationIds() {
    return this.fireStationIds;
  }
  
  public void setFireStationIds(List<Integer> fireStationIds) {
    this.fireStationIds = fireStationIds;
  }
}
