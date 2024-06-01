package com.fannss.taskmanagement.service;

import com.fannss.taskmanagement.DTO.CreateClientDTO;
import com.fannss.taskmanagement.entity.Client;
import com.fannss.taskmanagement.repository.ClientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(CreateClientDTO createClientDTO) {
        Client client = new Client();
        client.setName(createClientDTO.getName());
        client.setEmail(createClientDTO.getEmail());
        return clientRepository.save(client);
    }
}

