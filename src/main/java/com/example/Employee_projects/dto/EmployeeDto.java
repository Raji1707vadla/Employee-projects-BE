package com.example.Employee_projects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {
    private String id;                // Unique identifier for the employee
    private String name;              // Employee's full name
    private String position;          // Job title/position of the employee
    private String department;         // Department the employee belongs to
    private String email;              // Employee's email address
    private String phone;              // Employee's phone number
    private String address;            // Home address of the employee
    private List<String> projectIds;   // List of project IDs assigned to the employee
    private Double salary;             // Employee's salary
    private String gender; // Employee's gender'
    private String password;
    private Boolean isActive;

}
