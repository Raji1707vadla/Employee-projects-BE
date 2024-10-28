package com.example.Employee_projects.services.impl;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.Document.Project;
import com.example.Employee_projects.dto.ProjectDto;
import com.example.Employee_projects.exception.BadRequestException;
import com.example.Employee_projects.repository.ProjectRepository;
import com.example.Employee_projects.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ApiResponse getAllProjects() {
        List<ProjectDto> projects = projectRepository.findAll().stream().map(this::entityToProjectDto).toList();
        return new ApiResponse(HttpStatus.OK,"Get all projects",projects);
    }

    @Override
    public ApiResponse getProjectById(String id) {
        Project  project = projectRepository.findById(id).orElseThrow(()-> new BadRequestException("Project not found"));
        return new ApiResponse(HttpStatus.OK,"Get project",entityToProjectDto(project));
    }

    @Override
    public ApiResponse createProject(ProjectDto request) {
        Project project = dtoToEntity(request);
         projectRepository.save(project);
         return new ApiResponse(HttpStatus.OK, "Project created successfully");
    }

    @Override
    public ApiResponse updateProject(ProjectDto request) {
        Project project = dtoToEntity(request);
        projectRepository.save(project);
        return new ApiResponse(HttpStatus.OK, "Project updated successfully");
    }

    @Override
    public ApiResponse deleteProject(String id) {
        Project  project = projectRepository.findById(id).orElseThrow(()-> new BadRequestException("Project not found"));
        projectRepository.delete(project);
        return new ApiResponse(HttpStatus.OK, "Project deleted successfully");
    }

    public Project dtoToEntity(ProjectDto request) {
        Project project ;
        if(request.getId()!=null){
            project = projectRepository.findById(request.getId()).orElseThrow(()-> new BadRequestException("Project not found"));
            project.setId(request.getId());
        }else {
            project = new Project();
        }
        project.setDescription(request.getTitle());
        project.setDescription(request.getDescription());
        return project;
    }
    public  ProjectDto entityToProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        return projectDto;
    }

}

