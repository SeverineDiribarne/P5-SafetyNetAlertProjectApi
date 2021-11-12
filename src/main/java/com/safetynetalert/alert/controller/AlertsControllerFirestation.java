package com.safetynetalert.alert.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.service.SafetyAlertsDAOFirestation;
@RestController
public class AlertsControllerFirestation {
	
	@Autowired
	public SafetyAlertsDAOFirestation safetyAlertsDaoFirestation;
	
	private static final Logger log = LogManager.getLogger(); 
		/**
		 * Add a mapping FireStation/address
		 * @param firestationId
		 * @param address
		 */
		@PostMapping("/firestation")
		public void addAMappingFireStationIdWithAnAddress(@RequestParam Integer firestationId, @RequestParam String address ) {
			log.info("Request to add a mapping with input parameters: firestationId {}, address {}", firestationId, address);
			boolean result = safetyAlertsDaoFirestation.addMapping(firestationId, address);
			if( result )
			{
				log.info("Answer sent: The mapping has been added.");
			}
			else
			{
				log.info("Answer sent: The mapping could not be added.");
			}
		}

		/**
		 * Update the fireStation number based on an address
		 * @param firestation
		 */
		@PutMapping("/firestation")
		public void updateTheFireStationNumberBasedOnAnAddress(@RequestParam Integer oldFirestationId, @RequestParam Integer newFirestationId, @RequestParam String address) {
			log.info("Request to modify a mapping with input parameters: oldFirestationId {}, newFirestationId {}, address {}", oldFirestationId, newFirestationId, address);
			boolean result = safetyAlertsDaoFirestation.updateFirestationNumber(oldFirestationId, newFirestationId, address);
			if( result )
			{
				log.info("Answer sent: The fireStationId has been updated.");
			}
			else
			{
				log.info("Answer sent: The fireStationId could not be updated.");
			}
		}

		/**
		 * Delete the mapping by fireStationId or by address
		 * @param firestationId
		 * @param address
		 */
		@DeleteMapping("/firestation")
		public void deleteTheMappingByFirestationIdOrByAddress(@RequestParam(required = false) Integer firestationId, @RequestParam(required = false) String address) {
			log.info("Request to delete a mapping with parameters (not required) as input: firestationId {}, address {}", firestationId, address);
			//Delete the mapping by fireStationId
			if(firestationId != null) {
				boolean result = safetyAlertsDaoFirestation.deleteMappingByFirestationId(firestationId);
				if( result )
				{
					log.info("Answer sent: The mapping has been deleted by firestationId.");
				}
				else
				{
					log.info("Answer sent: The medical file could not be deleted by firestationId.");
				}
			}
			//Delete the mapping by address
			if(address != null) {
				boolean result = safetyAlertsDaoFirestation.deleteMappingByAddress(address);
				if( result )
				{
					log.info("Answer sent: The mapping has been deleted by address.");
				}
				else
				{
					log.info("Answer sent: The mapping could not be deleted by address.");
				}
			}
		}

	
}