package com.example.Employee_projects.Document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;                // Unique identifier for the employee
    private String name;              // Employee's full name
    private String position;          // Job title/position of the employee
    private String department;         // Department the employee belongs to
    private String email;              // Employee's email address
    private String phone;              // Employee's phone number
    private String address;            // Home address of the employee
    private List<String> projectIds;   // List of project IDs assigned to the employee
    private double salary;             // Employee's salary
    private String gender; // Employee's gender'


}
