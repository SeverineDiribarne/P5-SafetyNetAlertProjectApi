package com.safetynetalert.alert.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.repository.RepositoryFirestation;
@Service
public class SafetyAlertsInMemoryDAOFirestation implements SafetyAlertsDAOFirestation {

	RepositoryFirestation repository = new RepositoryFirestation();
	
	private static final Logger log = LogManager.getLogger(); 

	/**
	 * Add a mapping FireStation/address
	 * @param firestationId
	 * @param address
	 * @return true or false
	 */
	@Override
	public boolean addMapping(Integer firestationId, String address) {
		if(firestationId !=0 && address != null) {
			log.debug("return the state of adding of the mapping of the firestationId/address given in parameter");
			return repository.addMapping(firestationId, address);
		}
		log.debug("return false : the state of adding of the mapping of the firestationId/address given in parameter isn't carried out");
		return false;
	}

	/**
	 * Update the fireStationNumber based on an address
	 * @param oldFirestationId
	 * @param newfirestationId
	 * @param address
	 * @return true or false
	 */
	public boolean updateFirestationNumber(Integer oldFirestationId,Integer newfirestationId, String address) {
		if(oldFirestationId != 0 && newfirestationId != 0 && address != null) {
			log.debug("return the state of modification of firestationId number of the address given in parameter");
			 repository.updateFirestationNumber(oldFirestationId, newfirestationId, address);
			 return true;
		}
		log.debug("return false : the state of modification of firestationId number of the address given in parameter isn't carried out");
		return false;
	}
	
	/**
	 * Delete the mapping by firestationId
	 * @param firestationId
	 * @return true or false
	 */
	public boolean deleteMappingByFirestationId(Integer firestationId ) {
		if(firestationId != null) {
			log.debug("return the state of deletion of the mapping of the firestationId given in parameter");
			 repository.deleteMappingByFirestationId(firestationId);
			 return true;
		}
		log.debug("return false : if the state of deletion of the mapping of the firestationId given in parameter isn't carried out");
		return false;
	}
	
	/**
	 * Delete the mapping by address
	 * @param address
	 * @return true or false
	 */
	public boolean deleteMappingByAddress(String address) {
		if(address != null) {
			log.debug("returns the state of deletion of the mapping of the address given in parameter");
			return repository.deleteMappingByAddress(address);	
		}
		log.debug("return false : if the state of deletion of the mapping of the address given in parameter isn't carried out");
		return false;
	}
}
