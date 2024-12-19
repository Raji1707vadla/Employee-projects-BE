package com.example.Employee_projects.services.impl;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.CommonUtil;
import com.example.Employee_projects.Document.Employee;
import com.example.Employee_projects.Document.Project;
import com.example.Employee_projects.dto.EmployeeDto;
import com.example.Employee_projects.dto.ProjectDto;
import com.example.Employee_projects.exception.BadRequestException;
import com.example.Employee_projects.repository.EmployeeRepository;
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
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public ApiResponse getAllProjects() {
        List<ProjectDto> projects = projectRepository.findAllByOrderByIsActiveDesc().stream().map(this::entityToProjectDto).toList();
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
    @Override
    public ApiResponse inActiveProject(String projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new BadRequestException("Project not found"));
        if(project.getIsActive()!=null) {
            project.setIsActive(!project.getIsActive());
        }else {
            project.setIsActive(false);
        }
        projectRepository.save(project);
        return new ApiResponse(HttpStatus.OK, project.getIsActive()? "Project activated successfully":"Project inactivated successfully");
    }

    public Project dtoToEntity(ProjectDto request) {
        Project project ;
        if(request.getId()!=null){
            project = projectRepository.findById(request.getId()).orElseThrow(()-> new BadRequestException("Project not found"));
            project.setId(request.getId());
        }else {
            project = new Project();
        }
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        if(request.getStartDate()!=null){
            project.setStartDate(request.getStartDate());
        }
        if(request.getEndDate()!=null){
            project.setEndDate(request.getEndDate());
        }
        if(!CommonUtil.isValid(request.getManagerId())){
            System.out.println("entered manager");
            Employee manager = employeeRepository.findById(request.getManagerId()).orElseThrow(() -> new BadRequestException("Employee not found"));
           project.setManager(manager);
        }
        if(!CommonUtil.isValid(request.getLeadId())){
            System.out.println("entered lead");
            Employee manager = employeeRepository.findById(request.getLeadId()).orElseThrow(() -> new BadRequestException("Employee not found"));
            project.setLead(manager);
        }
        project.setIsActive(true);
        return project;
    }
    public  ProjectDto entityToProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        if (project.getIsActive()!=null) {
            projectDto.setIsActive(project.getIsActive());
        }else {
            projectDto.setIsActive(false);
        }
        if(project.getLead()!=null){
            projectDto.setLead(entityToDto(project.getLead()));
        }
        if(project.getManager()!=null){
            projectDto.setManager(entityToDto(project.getManager()));
        }
        return projectDto;
    }
    public EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setPhone(employee.getPhone());
        return employeeDto;
    }
}

