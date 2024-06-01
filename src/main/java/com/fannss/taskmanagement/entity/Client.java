package com.fannss.taskmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    private String companyName;

    @OneToMany(mappedBy = "client")
    private List<Project> projects;

    // Getters and Setters


    @Override
    public String toString() {
        return "Client{}";
    }
}
