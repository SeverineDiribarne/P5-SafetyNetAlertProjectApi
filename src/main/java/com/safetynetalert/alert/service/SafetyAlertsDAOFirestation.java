package com.safetynetalert.alert.service;

public interface SafetyAlertsDAOFirestation {

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 */
	boolean addMapping(Integer firestationId, String address);

	/**
	 * Update the fireStation number based on an address
	 * @param firestationId
	 * @param address
	 */
	boolean updateFirestationNumber(Integer oldFirestationId, Integer newfirestationId, String address);

	/**
	 * Delete mapping by firestationId
	 * @param firestationId
	 */
	boolean deleteMappingByFirestationId(Integer firestationId);

	/**
	 *  Delete mapping by address
	 * @param address
	 */
	boolean deleteMappingByAddress(String address);

}