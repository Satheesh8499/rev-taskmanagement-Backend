package com.fannss.taskmanagement.service;

import com.fannss.taskmanagement.DTO.CreateProjectDTO;
import com.fannss.taskmanagement.DTO.ProjectDTO;
import com.fannss.taskmanagement.DTO.TaskDTO;
import com.fannss.taskmanagement.DTO.UserDTO;
import com.fannss.taskmanagement.entity.Client;
import com.fannss.taskmanagement.entity.Project;
import com.fannss.taskmanagement.entity.Task;
import com.fannss.taskmanagement.entity.User;
import com.fannss.taskmanagement.repository.ClientRepository;
import com.fannss.taskmanagement.repository.ProjectRepository;
import com.fannss.taskmanagement.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ProjectDTO createProject(CreateProjectDTO createProjectDTO, Long userId, String role) {
        Project project = new Project();
        project.setProjectName(createProjectDTO.getProjectName());
        project.setProjectDescription(createProjectDTO.getProjectDescription());
        project.setStatus(createProjectDTO.getStatus());
        project.setStartDate(createProjectDTO.getStartDate());
        project.setEndDate(createProjectDTO.getEndDate());

        // Set client and project manager using IDs from the DTO
        Client client = clientRepository.findById(createProjectDTO.getClientId()).orElse(null);
        User projectManager = userRepository.findById(createProjectDTO.getProjectManagerId()).orElse(null);

        project.setClient(client);
        project.setProjectManager(projectManager);

        // Set team members
        List<User> teamMembers = createProjectDTO.getTeamMemberIds().stream()
                .map(id -> userRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        project.setTeamMembers(teamMembers);

        Project savedProject = projectRepository.save(project);

        return convertToDTO(savedProject);
    }

   public void deleteProject(Long id){
        projectRepository.deleteById(id);
   }
//    public Project getProjectById(Long projectId) {
//        return projectRepository.findById(projectId)
//                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));
//    }
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToDTO(project);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectDescription(project.getProjectDescription());
        projectDTO.setStatus(project.getStatus());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setClientId(project.getClient().getId());

        UserDTO projectManagerDTO = convertUserToDTO(project.getProjectManager());
        projectDTO.setProjectManager(projectManagerDTO);

        List<UserDTO> teamMemberDTOs = project.getTeamMembers().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
        projectDTO.setTeamMembers(teamMemberDTOs);

        List<TaskDTO> taskDTOs = project.getTasks().stream()
                .map(this::convertTaskToDTO)
                .collect(Collectors.toList());
        projectDTO.setTasks(taskDTOs);

        return projectDTO;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private TaskDTO convertTaskToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setStartDate(task.getStartDate());
        taskDTO.setCompletedDate(task.getEndDate());

       // UserDTO assignedToDTO = convertUserToDTO(task.getAssignedTo());
        taskDTO.setAssignedTo(task.getAssignedTo().getId());

        //UserDTO createdByDTO = convertUserToDTO(task.getCreatedBy());
        taskDTO.setAssignedBy(task.getCreatedBy().getId());

        return taskDTO;
    }
    public List<ProjectDTO> getProjectsByTaskAssignedTo(Long userId) {
        List<ProjectDTO> lpd=new ArrayList<>();
        List<Project> lp=projectRepository.findProjectsByTaskAssignedTo(userId);
         System.err.println(lp.size());
        for(Project l:lp){
            lpd.add(convertToDTO(l));
        }
        return lpd;
    }
    public List<ProjectDTO> getProjectByManagerId(Long id){
        List<ProjectDTO> lpd=new ArrayList<>();
        List<Project> lp=projectRepository.findByProjectManager_Id(id);
        for(Project l:lp){
            lpd.add(convertToDTO(l));
        }
        return lpd;
    }
    public List<ProjectDTO> getAllProjects(){
        List<ProjectDTO> lpd=new ArrayList<>();
        List<Project> lp=projectRepository.findAll();
        System.out.println(lp.size());
        for(Project l:lp){
            lpd.add(convertToDTO(l));
        }
        return lpd;
    }
}
