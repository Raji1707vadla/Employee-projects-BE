package com.example.Employee_projects.services.impl;
import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.CommonUtil;
import com.example.Employee_projects.Document.Employee;
import com.example.Employee_projects.config.JwtTokenUtils;
import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.dto.ResetPassword;
import com.example.Employee_projects.dto.SignInRequest;
import com.example.Employee_projects.dto.SignInResponseDto;
import com.example.Employee_projects.exception.BadRequestException;
import com.example.Employee_projects.repository.EmployeeRepository;
import com.example.Employee_projects.services.EmployeeService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public ApiResponse getAllEmployees() {
        List<EmployeeDto> employeeDtoList = employeeRepository.findAllByOrderByIsActiveDesc().stream().map(this::entityToDto).toList();
        return new ApiResponse(HttpStatus.OK, "Get all Employees ", employeeDtoList);
    }

    @Override
    public ApiResponse getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new BadRequestException("Employee not found"));
        return new ApiResponse(HttpStatus.OK, "Get Employee data", entityToDto(employee));
    }

    @Override
    public ApiResponse createEmployee(EmployeeDto request) {
        CommonUtil.isValidObject(request);
        Employee employee = dtoToEmployee(request);
        employeeRepository.save(employee);
        return new ApiResponse(HttpStatus.OK, "Employee Created Successfully");
    }
    @Override
    public ApiResponse signIn(SignInRequest signInDetails) throws JOSEException {
        Employee user = employeeRepository.findByPhone(signInDetails.getPhone());
        if (user != null) {
            boolean isMatch = passwordEncoder.matches(signInDetails.getPassword(), user.getPassword());
            if (isMatch) {
                SignInResponseDto response = new SignInResponseDto(user);
                response.setToken(jwtTokenUtils.getToken(user));
                return new ApiResponse(HttpStatus.OK, "login Successfully", response);
            } else return new ApiResponse(HttpStatus.UNAUTHORIZED, "Wrong Credentials !");
        }
        throw new BadRequestException("no user found with given mobile");
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
    @Override
    public ApiResponse inActiveEmployee(String employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new BadRequestException("Employee not found"));
        if(employee.getIsActive()!=null) {
            employee.setIsActive(!employee.getIsActive());
        }else {
            employee.setIsActive(false);
        }
        employeeRepository.save(employee);
        return new ApiResponse(HttpStatus.OK, employee.getIsActive() ? "Employee activated Successfully":  "Employee Inactivated Successfully");
    }

    public Employee dtoToEmployee(EmployeeDto request) {
        Employee employee;

        // Check if the request contains an existing employee ID
        if (request.getId() != null) {
            // Find the employee by ID, or throw an exception if not found
            employee = employeeRepository.findById(request.getId())
                    .orElseThrow(() -> new BadRequestException("Employee not found"));

            // Check for uniqueness of the phone number
            if (employeeRepository.findByPhone(request.getPhone()) != null
                    && !employee.getId().equals(request.getId())) {
                throw new BadRequestException("Mobile number already exists");
            }
            if(CommonUtil.isValid(request.getPassword())) {
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            }else {
                employee.setPassword(passwordEncoder.encode(request.getPassword()));
            }
        } else {
            if(CommonUtil.isValid(request.getPassword())) {
                throw new BadRequestException("Employee Password should not be null");
            }
            // If the employee ID is null, create a new Employee instance
            employee = new Employee();

            // Ensure the phone number is unique for new employee
            if (employeeRepository.findByPhone(request.getPhone()) != null) {
                throw new BadRequestException("Mobile number already exists");
            }
            employee.setPassword( passwordEncoder.encode(request.getPassword()));
        }

        // Set employee properties from the DTO
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setAddress(request.getAddress());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setDepartment(request.getDepartment());
        employee.setGender(request.getGender());
        employee.setIsActive(true);

        return employee;
    }
    @Override
    public ApiResponse resetPassword(ResetPassword resetPassword){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = (Employee) authentication.getPrincipal();
        boolean isMatch = passwordEncoder.matches(resetPassword.getOldPassword(), employee.getPassword());
        System.out.println(employee.getPassword());
        System.out.println(resetPassword.getOldPassword());
        boolean isMatchNew = passwordEncoder.matches(resetPassword.getNewPassword(), employee.getPassword());
        if(!isMatch){
            throw new BadRequestException("Old Password is incorrect");
        }
        if(isMatchNew){
            throw new BadRequestException("New Password should not be same as old password");
        }
        employee.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        employeeRepository.save(employee);
        return new ApiResponse(HttpStatus.OK, "Password Reset Successfully");
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
        if (employee.getIsActive()!=null) {
            employeeDto.setIsActive(employee.getIsActive());
        }else {
            employeeDto.setIsActive(false);
        }
        return employeeDto;
    }
}

