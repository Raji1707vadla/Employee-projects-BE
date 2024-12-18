package com.example.Employee_projects.controller;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.Document.Employee;
import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.dto.SignInRequest;
import com.example.Employee_projects.services.EmployeeService;
import com.nimbusds.jose.JOSEException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // Get all employees
    @GetMapping("/get-all-employees")
    public ApiResponse getAllEmployees() {
        logger.info("Get All Employee Service Started");
        ApiResponse apiResponse=  employeeService.getAllEmployees();
        logger.info("Get All Employee Service Completed");
        return apiResponse;
    }

    // Get an employee by ID
    @GetMapping("/get-employee-by-id/{id}")
    public ApiResponse getEmployeeById(@PathVariable String id) {
        logger.info("Get by employee id service started");
        ApiResponse  apiResponse = employeeService.getEmployeeById(id);
        logger.info("Get by employee by id service completed");
        return apiResponse;
    }

    // Create a new employee
    @PostMapping("/create-employee")
    public ApiResponse createEmployee(@RequestBody EmployeeDto employee) {
        logger.info("Create Employee Service Started");
        ApiResponse apiResponse = employeeService.createEmployee(employee);
        logger.info("Create Employee Service Completed");
        return apiResponse;
    }

    @PostMapping("/sign-in-employee")
    public ApiResponse signIn(@RequestBody SignInRequest request) throws JOSEException {
        logger.info("Sign In Employee Service Started");
        ApiResponse apiResponse = employeeService.signIn(request);
        logger.info("Sign In Employee Service Completed");
        return apiResponse;
    }

    // Update an existing request
    @PutMapping("/update-employee")
    public ApiResponse updateEmployee( @RequestBody EmployeeDto request) {
        logger.info("Update Employee Service Started");
        ApiResponse apiResponse = employeeService.updateEmployee(request);
        logger.info("Update Employee Service Completed");
        return apiResponse;
    }

    // Delete an employee
    @DeleteMapping("/delete-employee/{id}")
    public ApiResponse deleteEmployee(@PathVariable String id) {
        logger.info("Delete Employee Service Started");
        ApiResponse apiResponse = employeeService.deleteEmployee(id);
        logger.info("Delete Employee Service Completed");
        return apiResponse;
    }
    @DeleteMapping("/active-or-inactive-employee/{employeeId}")
    public ApiResponse inActiveEmployee(@PathVariable String employeeId) {
        logger.info("Active Employee Service Started");
        ApiResponse apiResponse = employeeService.inActiveEmployee(employeeId);
        logger.info("Active Employee Service Completed");
        return apiResponse;
    }

}
