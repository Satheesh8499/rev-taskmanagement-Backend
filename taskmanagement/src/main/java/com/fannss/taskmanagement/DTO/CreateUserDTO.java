package com.fannss.taskmanagement.DTO;


import lombok.Data;

@Data
public class CreateUserDTO {
    public Long id;
    public String username;
    public String email;
    public String password;
    public String role;

    public CreateUserDTO() {}

    public CreateUserDTO(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
