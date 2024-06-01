package com.fannss.taskmanagement.service;

import com.fannss.taskmanagement.DTO.CreateUserDTO;
import com.fannss.taskmanagement.DTO.LoginDTO;
import com.fannss.taskmanagement.DTO.UserDTO;
import com.fannss.taskmanagement.entity.User;
import com.fannss.taskmanagement.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setRole(createUserDTO.getRole());
        user.setPassword(createUserDTO.getPassword());

        return userRepository.save(user);
    }
   public List<CreateUserDTO> getAllUsers(){
        List<CreateUserDTO> cud=new ArrayList<>();
        List<User> us=userRepository.findAll();
        for(User u:us){
            CreateUserDTO cd=new CreateUserDTO();
            cd.setId(u.getId());
            cd.setUsername(u.getUsername());
            cd.setEmail(u.getEmail());
            cd.setPassword(u.getPassword());
            cd.setRole(u.getRole());
            cud.add(cd);
        }
        return  cud;
   }
    public List<UserDTO> getUsersByProjectId(Long projectId) {
        List<UserDTO> userDTO=new ArrayList<>();

        List<User> us= userRepository.findUsersByProjectId(projectId);
        for(User u:us){
             UserDTO ud=new UserDTO();
             ud.setId(u.getId());
             ud.setUsername(u.getUsername());
             ud.setEmail(u.getEmail());
             ud.setRole(u.getRole());
             userDTO.add(ud);
        }
        return userDTO;
    }
    public CreateUserDTO updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setEmail(user.getEmail());
            User u= userRepository.save(user);
            return convertToCreateUserDTO(u);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public Optional<User> login(LoginDTO loginDTO) {
        return userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    }
   public User convertUser(CreateUserDTO createUserDTO){
       User user = new User();
       user.setId(user.getId());
       user.setUsername(createUserDTO.getUsername());
       user.setEmail(createUserDTO.getEmail());
       user.setRole(createUserDTO.getRole());
       user.setPassword(createUserDTO.getPassword());
       return  user;
   }
    public CreateUserDTO convertToCreateUserDTO(User user) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setId(user
                .getId());
        createUserDTO.username = user.getUsername();
        createUserDTO.email = user.getEmail();
        createUserDTO.password = user.getPassword();
        createUserDTO.role = user.getRole();
        return createUserDTO;
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}

