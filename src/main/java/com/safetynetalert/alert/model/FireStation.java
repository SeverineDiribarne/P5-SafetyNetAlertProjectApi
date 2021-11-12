package com.safetynetalert.alert.model;

import java.util.ArrayList;
import java.util.List;

public class FireStation {
	
	//Attribute
	private int stationId;
	private List<Address> coveredAddress = new ArrayList<>();

	/**
	 * @return the coveredAddress
	 */
	public List<Address> getCoveredAddress() {
		return coveredAddress;
	}

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
	 * Add Address
	 * @param newCoveredAddress
	 */
	public void addAddress(Address newCoveredAddress) {
		coveredAddress.add(newCoveredAddress);
	}

	@Override
	public String toString() {
		return "FireStation [stationId=" + stationId + "]";
	}
}
