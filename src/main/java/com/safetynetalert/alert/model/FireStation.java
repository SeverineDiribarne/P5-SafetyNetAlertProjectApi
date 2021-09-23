package com.safetynetalert.alert.model;

public class FireStation {
	
	//Attribute
	private int stationId;

	/**
	 * Getter StationId
	 * @return this stationId
	 */
	public int getStationId() {
		return this.stationId;
	}

	/**
	 * Setter StationId
	 * @param stationId
	 */
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	/**
	 * ToString method
	 */
	public String toString() {
		return "  StationId : " + getStationId();
	}
}
