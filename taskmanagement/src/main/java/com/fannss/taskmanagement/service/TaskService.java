package com.fannss.taskmanagement.service;

import com.fannss.taskmanagement.DTO.CreateTaskDTO;
import com.fannss.taskmanagement.DTO.TaskDTO;
import com.fannss.taskmanagement.entity.Project;
import com.fannss.taskmanagement.entity.Task;
import com.fannss.taskmanagement.entity.User;
import com.fannss.taskmanagement.repository.ProjectRepository;
import com.fannss.taskmanagement.repository.TaskRepository;
import com.fannss.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Task createTask(CreateTaskDTO createTaskDTO) {
        Task task = new Task();
        task.setTaskName(createTaskDTO.getName());
        task.setTaskDescription(createTaskDTO.getDescription());
        task.setStartDate(createTaskDTO.getStartDate());
        task.setEndDate(createTaskDTO.getCompletedDate());
        task.setStatus(createTaskDTO.getStatus());
        task.setPriority(createTaskDTO.getPriority());

        Project project = projectRepository.findById(createTaskDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);

        User assignedTo = userRepository.findById(createTaskDTO.getAssignedTo())
                .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        task.setAssignedTo(assignedTo);

        User createdBy = userRepository.findById(createTaskDTO.getAssignedBy())
                .orElseThrow(() -> new RuntimeException("Creating user not found"));
        task.setCreatedBy(createdBy);

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getCreatedBy().getId().equals(userId) ) {
            taskRepository.delete(task);
        } else {
            throw new RuntimeException("User not authorized to delete this task");
        }
    }
    public Task updateTask(Long id, TaskDTO taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        task.setTaskName(taskDetails.getTaskName());
        task.setTaskDescription(taskDetails.getTaskDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setStartDate(taskDetails.getStartDate());
        task.setEndDate(taskDetails.getCompletedDate());
        task.setProject(task.getProject());
        task.setAssignedTo(task.getAssignedTo());
        task.setCreatedBy(task.getCreatedBy());

        return taskRepository.save(task);
    }

    public TaskDTO getTask(Long id) {
        Optional<Task> t = taskRepository.findById(id);
        TaskDTO taskDTO = new TaskDTO();
        if (t.isPresent()) {
            Task task = t.get();
            taskDTO.setId(task.getId());
            taskDTO.setProjectId(task.getProject().getId());
            taskDTO.setTaskName(task.getTaskName());
            taskDTO.setTaskDescription(task.getTaskDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setStartDate(Date.valueOf(task.getStartDate().toString()));
            taskDTO.setDueDate(task.getDueDate() != null ? Date.valueOf(task.getDueDate().toString()) : null);
            taskDTO.setCompletedDate(task.getEndDate() != null ? Date.valueOf(task.getEndDate().toString()) : null);
            taskDTO.setAssignedTo(task.getAssignedTo().getId());
            taskDTO.setAssignedBy(task.getCreatedBy().getId());
        }
        return taskDTO;
    }
    public TaskDTO createTaskByUser(Long id,TaskDTO taskDTO){
        Task task=new Task();
         Project project=projectRepository.getById(taskDTO.getProjectId());
         task.setProject(project);
         task.setTaskName(taskDTO.getTaskName());
         task.setTaskDescription(taskDTO.getTaskDescription());
         task.setStatus(taskDTO.getStatus());
         task.setPriority(taskDTO.getPriority());
         task.setDueDate(taskDTO.getDueDate());
         User assignTo=userRepository.getById(taskDTO.getAssignedTo());
         task.setAssignedTo(assignTo);
         User user=userRepository.getById(id);
         task.setCreatedBy(user);
         Task t=taskRepository.save(task);
       return convertDTO(t);
    }

    public TaskDTO convertDTO(Task task){
         TaskDTO  dto=new TaskDTO();
         dto.setId(task.getId());
         dto.setTaskName(task.getTaskName());
         return  dto;
    }
}
