package com.safetynetalert.alert.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynetalert.alert.model.Person;
import com.safetynetalert.alert.repository.RepositoryPerson;
@Service
public class SafetyAlertsInMemoryDAOPerson implements SafetyAlertsDAOPerson {
	
	RepositoryPerson repository = new RepositoryPerson();
	
	private static final Logger log = LogManager.getLogger(); 
	
		/**
		 * add persons
		 * @param person
		 * @return person added
		 */
		@Override
		public boolean addPersons(Person person) {
			if(person != null) {
			log.debug("return the state of adding of the person with request body given.");
			return repository.addPerson(person);
			}
			log.debug("return the state of adding of the person with request body given isn't carried out");
			return false;
		}

		/**
		 * Update person
		 * @param firstName
		 * @param lastName
		 * @param person
		 */
		@Override
		public boolean updatePerson( String firstName, String lastName, Person person) {
			if(firstName != null && lastName!= null && person !=null) {
			log.debug("return the state of updating of the person found of the firstName/lastName given in parameter");
			 return repository.updatePerson(firstName, lastName, person);
			}
			log.debug("return the state of updating of the person found of the firstName/lastName given in parameter isn't carried out");
			return false;
		}

		/**
		 * Delete a person
		 * @param firstName
		 * @param lastName
		 */
		@Override
		public boolean deletePerson(String firstName, String lastName) {
			if(firstName != null && lastName != null) {
			log.debug("return the state of deletion of the person found of the firstName/lastName given in parameter");
			return repository.deletePerson(firstName, lastName);
			}
			log.debug("return false : the state of deletion of the person found of the firstName/lastName given in parameter isn't carried out");
			return false;
		}
}
