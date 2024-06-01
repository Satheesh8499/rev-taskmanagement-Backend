package com.fannss.taskmanagement.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="rev_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;
    private String password;

    private String role;  // Manager or Team Member

    @OneToMany(mappedBy = "projectManager")
    private List<Project> managedProjects;

    @ManyToMany(mappedBy = "teamMembers")
    private List<Project> projects;

    @OneToMany(mappedBy = "assignedTo")
    private List<Task> tasks;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

    // Getters and Setters
}

