package com.fannss.taskmanagement.repository;

import com.fannss.taskmanagement.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
