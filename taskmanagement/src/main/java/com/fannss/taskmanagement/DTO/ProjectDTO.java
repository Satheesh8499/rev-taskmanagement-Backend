package com.fannss.taskmanagement.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String projectName;
    private String projectDescription;
    private String status;
    private String startDate;
    private String endDate;
    private Long clientId;
    private UserDTO projectManager;
    private List<UserDTO> teamMembers;
    private List<TaskDTO> tasks;

}
