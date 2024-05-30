package com.fannss.taskmanagement.controller;

import com.fannss.taskmanagement.DTO.CreateProjectDTO;
import com.fannss.taskmanagement.DTO.ProjectDTO;
import com.fannss.taskmanagement.entity.Project;
import com.fannss.taskmanagement.entity.User;
import com.fannss.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public  ResponseEntity<List<ProjectDTO>> getAll()
    {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(
           @RequestBody CreateProjectDTO createProjectDTO,
           @RequestHeader("userId") Long userId,
           @RequestHeader("role") String role) {
        ProjectDTO createdProject = projectService.createProject(createProjectDTO, userId, role);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long projectId) {
        ProjectDTO project = projectService.getProjectById(projectId);
        System.out.println(project);
        ResponseEntity<ProjectDTO> projectDTOResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        return projectDTOResponseEntity;
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-task-assignee/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByTaskAssignee(@PathVariable Long userId) {
        List<ProjectDTO> projects = projectService.getProjectsByTaskAssignedTo(userId);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/by-project-manager/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByProjectManager(@PathVariable Long userId) {
        List<ProjectDTO> projects = projectService.getProjectByManagerId(userId);
        return ResponseEntity.ok(projects);
    }




}

