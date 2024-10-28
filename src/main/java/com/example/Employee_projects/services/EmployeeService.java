package com.example.Employee_projects.services;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.dto.EmployeeDto;

public interface EmployeeService {
    // Get all employees
    ApiResponse getAllEmployees();

    // Get an employee by ID
    ApiResponse getEmployeeById(String id);

    // Create a new employee
    ApiResponse createEmployee(EmployeeDto request);

    // Update an existing employee
    ApiResponse updateEmployee(EmployeeDto request);

    // Delete an employee
    ApiResponse deleteEmployee(String id);
}
