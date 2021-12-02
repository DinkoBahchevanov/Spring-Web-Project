package com.example.demo.repositories;

import com.example.demo.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    Service findByName(String name);
}
