package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    UserController(UserRepository repository, UserService userService) {
        this.userRepository = repository;
        this.userService = userService;
    }
    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/register")
    ModelAndView getRegister(Model model) {
        User user = new User();
        ModelAndView mav = new ModelAndView("register.html");
        model.addAttribute("user", user);

        return mav;
    }

    @PostMapping("/register")
    public RedirectView postRegister(@ModelAttribute("user") User user) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userService.registerEmployee(user);
            return new RedirectView("http://localhost:8080/users");
        }catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @GetMapping("/login")
    ModelAndView getLogin(Model model) {
        User user = new User();
        ModelAndView mav = new ModelAndView("login.html");
        model.addAttribute("user", user);
        return mav;
    }

    @GetMapping("/logout")
    ModelAndView getLogout(Model model) {
        User user = new User();
        ModelAndView mav = new ModelAndView("logout.html");
        model.addAttribute("user", user);
        return mav;
    }

    @GetMapping("/users_details")
    ModelAndView getUsersDetails(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        List<User> users = userService.getAllEmployees();
        model.addAttribute("users", users);
        ModelAndView view = new ModelAndView("users_details.html");
        return view;
    }

    @GetMapping("/users")
    public ModelAndView showAllEmployees(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        List<User> users = userService.getAllEmployees();
        model.addAttribute("users", users);
        return new ModelAndView("users.html");
    }
}
