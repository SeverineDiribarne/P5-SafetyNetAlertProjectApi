package com.safetynetalert.alert.model;

public class FireStation {
  private int stationId;
  
  public int getStationId() {
    return this.stationId;
  }
  
  public void setStationId(int stationId) {
    this.stationId = stationId;
  }
  
  public String toString() {
    return "  StationId : " + getStationId();
  }
}
