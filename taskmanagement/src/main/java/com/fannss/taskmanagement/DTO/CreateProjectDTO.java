package com.fannss.taskmanagement.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CreateProjectDTO {
    private String projectName;
    private String projectDescription;
    private String status;
    private String startDate;
    private String endDate;
    private Long clientId;
    private Long projectManagerId;
    private List<Long> teamMemberIds;

}

