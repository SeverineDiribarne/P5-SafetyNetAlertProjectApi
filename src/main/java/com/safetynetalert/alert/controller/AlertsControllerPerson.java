package com.safetynetalert.alert.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.service.SafetyAlertsDAOPerson;
@RestController
public class AlertsControllerPerson {
	
	@Autowired
	public SafetyAlertsDAOPerson safetyAlertsDaoPerson;

	private static final Logger log = LogManager.getLogger(); 

		/**
		 * Create a new person
		 * @param person
		 * @return person added
		 */
		@PostMapping(value = "/person")
		public void createNewPerson(@RequestBody Person person) {
			log.info("Request to add a person with request body: person {}", person );
			boolean result = safetyAlertsDaoPerson.addPersons(person);
			if( result )
			{
				log.info("Answer sent: The person has been added.");
			}
			else
			{
				log.info("Answer sent: The person could not be added.");
			}
		}

		/**
		 * Update a person with his firstName and lastName
		 * @param person
		 */
		@PutMapping("/person")
		public void updatePerson(@RequestParam("firstName") String firstName, 
				@RequestParam("lastName") String lastName, @RequestBody Person person) {
			log.info("Person modification request with input parameters: firstName {}, lastName {}, and request body: person {}", firstName, lastName, person );
			boolean result = safetyAlertsDaoPerson.updatePerson(firstName, lastName, person);
			if( result )
			{
				log.info("Answer sent: The person has been updated by firstName and lastName.");
			}
			else
			{
				log.info("Answer sent: The person could not be updated by firstName and lastName.");
			}
		}

		/**
		 * Delete a person
		 * @param person
		 */
		@DeleteMapping ("/person")
		public void deletePerson(@RequestParam("firstName") String firstName, 
				@RequestParam("lastName") String lastName) {
			log.info("Request to delete a person with input parameters: firstName {}, lastName {}", firstName, lastName);
			boolean result = safetyAlertsDaoPerson.deletePerson(firstName, lastName);
			if( result )
			{
				log.info("Answer sent: The person has been deleted by firstName and lastName.");
			}
			else
			{
				log.info("Answer sent: The person could not be deleted by firstName and lastName.");
			}
		}

}