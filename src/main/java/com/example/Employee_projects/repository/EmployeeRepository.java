package com.example.Employee_projects.repository;

import com.example.Employee_projects.Document.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
