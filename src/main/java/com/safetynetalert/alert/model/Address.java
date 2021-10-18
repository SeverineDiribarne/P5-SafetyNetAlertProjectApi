package com.safetynetalert.alert.model;

import java.util.List;

public class Address {

	//Attributes
	private String street;
	private String city;
	private String zip;
	private List<Integer> fireStationIds;

	/**
	 * getter street
	 * @return this street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Setter street
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * getter city
	 * @return this city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Setter city
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * getter zip
	 * @return this zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * Setter zip code
	 * @param zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * getter fireStationIds
	 * @return the fireStationIds
	 */
	public List<Integer> getFireStationIds() {
		return fireStationIds;
	}

	/**
	 * Setter FireStationIds
	 * @param fireStationIds
	 */
	public void setFireStationIds(List<Integer> fireStationIds) {
		this.fireStationIds = fireStationIds;
	}

	/**
	 * add FirestationId
	 * @param firestationId
	 */
	public void addFirestationId(Integer firestationId) {
		if(! fireStationIds.contains(firestationId)) {
			fireStationIds.add(firestationId);
		}
	}

	/**
	 * remove firestationId
	 * @param firestationId
	 */
	public void removeFirestationId(Integer firestationId) {
		fireStationIds.remove(firestationId);
	}

	/**
	 * update firestationId
	 * @param oldFirestationId
	 * @param newFirestationId
	 */
	public void updateFirestationId(Integer oldFirestationId, Integer newFirestationId) {
		removeFirestationId(oldFirestationId);
		addFirestationId(newFirestationId);
	}

	/**
	 * Remove All FirestationIds
	 */
	public void removeAllFirestationIds() {
		fireStationIds.clear();	
	}

	/**
	 * contains firestationId
	 * @param firestationId
	 * @return firestationId exist
	 */
	public boolean containsFirestationId(Integer firestationId) {
		return fireStationIds.contains(firestationId);
	}

	/**
	 * equals firestationId
	 * @param stations
	 * @return firestationId exist
	 */
	public boolean equalsFirestationId(List<Integer> stations) {
		boolean elementfound = false;

		for(Integer station : stations) {
			if(fireStationIds.contains(station)) {
				elementfound = true;
			}
		}
		return elementfound;
	}
	//	/**
	//	 * ToString method
	//	 */
	//	public String toString() {
	//		return "   Street : " + getStreet() + 
	//				"  City : " + getCity() + 
	//				"  Zip Code : " + getZip(); 
	//				for(FireStation firestationId : FirestationIds ) {
	//					" FirestationIds : "
	//				}
	//				  
	//	}

}
