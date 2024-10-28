package com.example.Employee_projects.repository;

import com.example.Employee_projects.Document.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}

