package com.example.Employee_projects.services.impl;
import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.Document.Employee;
import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.exception.BadRequestException;
import com.example.Employee_projects.repository.EmployeeRepository;
import com.example.Employee_projects.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ApiResponse getAllEmployees() {
        List<EmployeeDto> employeeDtoList = employeeRepository.findAll().stream().map(this::entityToDto).toList();
        return new ApiResponse(HttpStatus.OK, "Get all Employees ", employeeDtoList);
    }

    @Override
    public ApiResponse getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new BadRequestException("Employee not found"));
        return new ApiResponse(HttpStatus.OK, "Get Employee data", entityToDto(employee));
    }

    @Override
    public ApiResponse createEmployee(EmployeeDto request) {
        Employee employee = dtoToEmployee(request);
        employeeRepository.save(employee);
        return new ApiResponse(HttpStatus.OK, "Employee Created Successfully");
    }

    @Override
    public ApiResponse updateEmployee(EmployeeDto request) {
        if(request.getId() == null){
            throw new BadRequestException("please provide employee id");
        }
        Employee employee = dtoToEmployee(request);
        employeeRepository.save(employee);
        return new ApiResponse(HttpStatus.OK, "Employee Updated Successfully");
    }

    @Override
    public ApiResponse deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new BadRequestException("Employee not found"));
        employeeRepository.delete(employee);
        return new ApiResponse(HttpStatus.OK, "Employee deleted successfully");
    }

    public Employee dtoToEmployee(EmployeeDto request) {
        Employee employee;
        if (request.getId() != null) {
            employee = employeeRepository.findById(request.getId()).orElseThrow(() -> new BadRequestException("Employee not found"));
            employee.setId(request.getId());
        } else {
            employee = new Employee();
        }
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());
        employee.setGender(request.getGender());
        return employee;
    }

    public EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setGender(employee.getGender());
        return employeeDto;
    }
}

