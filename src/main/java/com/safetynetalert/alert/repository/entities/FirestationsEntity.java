package com.safetynetalert.alert.repository.entities;

public class FirestationsEntity {
	
	//Attributes
	private String address;
	private String station;
	
	/**
	 * Getter address
	 * @return this address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Setter address
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter station
	 * @return this station
	 */
	public String getStation() {
		return this.station;
	}

	/**
	 * Setter station
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}
}
