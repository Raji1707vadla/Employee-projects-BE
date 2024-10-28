package com.example.Employee_projects.services;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.dto.ProjectDto;

public interface ProjectService {
    ApiResponse getAllProjects();

    ApiResponse getProjectById(String id);

    ApiResponse createProject(ProjectDto request);

    ApiResponse updateProject(ProjectDto request);

    ApiResponse deleteProject(String id);
}
