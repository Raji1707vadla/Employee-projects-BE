package com.example.Employee_projects.repository;

import com.example.Employee_projects.Document.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findAllByOrderByIsActiveDesc();
}

