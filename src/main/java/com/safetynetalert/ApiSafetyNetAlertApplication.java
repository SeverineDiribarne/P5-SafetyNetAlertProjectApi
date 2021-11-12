package com.safetynetalert;

import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiSafetyNetAlertApplication extends SpringBootServletInitializer {

	// Entry point
	public static void main(String[] args) {
		RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
		SpringApplication.run(ApiSafetyNetAlertApplication.class, args);
		repository.initialize();
	}
}
