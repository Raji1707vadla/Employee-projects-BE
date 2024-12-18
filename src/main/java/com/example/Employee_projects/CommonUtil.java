package com.example.Employee_projects;

import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.exception.BadRequestException;

public class CommonUtil {

    public static void isValidObject(EmployeeDto employeeDto){
        if(isValid(employeeDto.getName())){
            throw new BadRequestException("Employee Name should not be null");
        }else if(isValid(employeeDto.getGender())){
            throw new BadRequestException("Employee Gender should not be null");
        }else if(isValid(employeeDto.getEmail())){
            throw new BadRequestException("Employee Email should not be null");
        }else if(isValid(employeeDto.getDepartment())){
            throw new BadRequestException("Employee Department should not be null");
        }else if(isValid(employeeDto.getPhone())){
            throw new BadRequestException("Employee Phone should not be null");
        }else if(!isValid(employeeDto.getSalary())){
            throw new BadRequestException("Employee Salary should not be null");
        }
    }

    public static boolean isValid(String string){
        return string == null || string.isEmpty();
    }
    public static boolean isValid(Double string){
        return string != null;
    }
}
