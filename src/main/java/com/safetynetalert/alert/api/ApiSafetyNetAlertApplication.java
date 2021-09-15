package com.safetynetalert.alert.api;

import com.safetynetalert.alert.repository.RecoveryOfJsonDataInJavaObject;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiSafetyNetAlertApplication extends SpringBootServletInitializer {
  public static void main(String[] args) throws IOException {
    RecoveryOfJsonDataInJavaObject repository = RecoveryOfJsonDataInJavaObject.getInstance();
    SpringApplication.run(ApiSafetyNetAlertApplication.class, args);
    repository.initialize();
  }
}
