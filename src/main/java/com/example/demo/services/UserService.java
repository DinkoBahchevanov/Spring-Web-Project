package com.example.demo.services;

import com.example.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    void registerEmployee(User employee);
    List<User> getAllEmployees();
}
