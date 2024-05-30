package com.fannss.taskmanagement.controller;


import com.fannss.taskmanagement.DTO.CreateClientDTO;
import com.fannss.taskmanagement.entity.Client;
import com.fannss.taskmanagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDTO createClientDTO) {
        Client client = clientService.createClient(createClientDTO);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}

