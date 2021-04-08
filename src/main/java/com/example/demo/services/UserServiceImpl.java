package com.example.demo.services;

import com.example.demo.entities.CustomUserDetails;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository employeeRepository) {
        this.userRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println(user.toString());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public void registerEmployee(User user) {
        if (validateEmployee(user)) {
            this.userRepository.saveAndFlush(user);
            return;
        }
        throw new IllegalStateException("Employee with that name already exists!");
    }

    @Override
    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }

    private boolean validateEmployee(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            return true;
        }
        return false;
    }

}
