package com.example.demo.controllers;

import com.example.demo.entities.Car;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
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
        }catch (ConstraintViolationException constraintViolationException) {
            return new RedirectView("http://localhost:8080/register");
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

    @GetMapping("/users/{id}")
    User one(@PathVariable int id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

//    @GetMapping("/users/delete/{id}")
//    ModelAndView deleteUser(@PathVariable Integer id) {
//        userRepository.deleteById(id);
//        System.out.println("baba");
//        return new ModelAndView("users.html");
//    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable("id") int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        System.out.println("deleted");
        return new RedirectView("http://localhost:8080/users_details");
    }

    @PostMapping("/update/{id}")
    public RedirectView updateUser(@PathVariable("id") int id, User user) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        String password = byEmail.getPassword();
        List<Car> cars = byEmail.getCars();
        user.setPassword(password);
        user.setCars(cars);
        try {
            userRepository.save(user);
        }catch (ConstraintViolationException constraintViolationException) {
            return new RedirectView("http://localhost:8080/update/{id}");
        }

        return new RedirectView("http://localhost:8080/users_details");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") int id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return new ModelAndView("update-user.html");
    }
}
