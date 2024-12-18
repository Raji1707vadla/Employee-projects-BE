package com.example.Employee_projects.controller;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.dto.ProjectDto;
import com.example.Employee_projects.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService projectService;

    @GetMapping("/get-all-project")
    public ApiResponse getAllProjects() {
        logger.info("get all project service started");
        ApiResponse apiResponse=  projectService.getAllProjects();
        logger.info("get all project service completed");
        return apiResponse;
    }

    @GetMapping("/get-project-by-id/{id}")
    public ApiResponse getProjectById(@PathVariable String id) {
        logger.info("get project id service started");
        ApiResponse apiResponse = projectService.getProjectById(id);
        logger.info("get project id service completed");
        return apiResponse;
    }

    @PostMapping("/create-project")
    public ApiResponse createProject(@RequestBody ProjectDto project) {
        logger.info("create project service started");
        ApiResponse apiResponse= projectService.createProject(project);
        logger.info("create project service completed");
        return apiResponse;
    }

    @PutMapping("/update-project")
    public ApiResponse updateProject(@RequestBody ProjectDto project) {
        logger.info("update project service started");
        ApiResponse apiResponse = projectService.updateProject(project);
        logger.info("update project service completed");
        return apiResponse;
    }

    @DeleteMapping("/delete-project/{id}")
    public ApiResponse deleteProject(@PathVariable String id) {
        logger.info("delete project service started");
        ApiResponse apiResponse = projectService.deleteProject(id);
        logger.info("delete project service completed");
        return apiResponse;
    }

    @DeleteMapping("/active-or-inactive-project/{projectId}")
    public ApiResponse inActiveProject(@PathVariable String projectId) {
        logger.info("Active project service started");
        ApiResponse apiResponse = projectService.inActiveProject(projectId);
        logger.info("Active project service completed");
        return apiResponse;
    }
}

