package com.example.Employee_projects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Employee_projects")
public class EmployeeProjectsApplication {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeProjectsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeProjectsApplication.class, args);
		logger.info("***************************************************");
		logger.info("*       EMPLOYEE-PROJECT SERVER STARTED       *");
		logger.info("***************************************************");
	}
}
