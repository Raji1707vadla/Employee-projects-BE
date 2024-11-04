package com.example.Employee_projects.services;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.dto.SignInRequest;
import com.nimbusds.jose.JOSEException;

public interface EmployeeService {
    // Get all employees
    ApiResponse getAllEmployees();

    // Get an employee by ID
    ApiResponse getEmployeeById(String id);

    // Create a new employee
    ApiResponse createEmployee(EmployeeDto request);

    ApiResponse signIn(SignInRequest signInDetails) throws JOSEException;

    // Update an existing employee
    ApiResponse updateEmployee(EmployeeDto request);

    // Delete an employee
    ApiResponse deleteEmployee(String id);
}
