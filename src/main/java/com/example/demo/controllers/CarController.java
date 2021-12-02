package com.example.demo.controllers;

import com.example.demo.entities.Car;
import com.example.demo.entities.User;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class CarController {

    private CarRepository carRepository;
    private UserRepository userRepository;

    @Autowired
    public CarController(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/cars")
    public ModelAndView showAllCars(Model model) {
        Car car = new Car();
        model.addAttribute("car",car);
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return new ModelAndView("cars.html");
    }

    @PostMapping("/add-car")
    public RedirectView addCar(Car car) {
        User byEmail = userRepository.findByEmail(car.getUser().getEmail());
        if (byEmail == null) {
            return new RedirectView("http://localhost:8080/add-car");
        }
        car.setUser(byEmail);
        try {
            carRepository.save(car);
        }catch (ConstraintViolationException exception) {
            return new RedirectView("http://localhost:8080/add-car");
        }
        byEmail.getCars().add(car);

        return new RedirectView("http://localhost:8080/cars");
    }

    @GetMapping("/add-car")
    public ModelAndView getAddCarView(Car car) {
        return new ModelAndView("add-car.html");
    }

    @GetMapping("car/delete/{id}")
    public RedirectView deleteCar(@PathVariable("id") int id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        carRepository.delete(car);
        System.out.println("deleted");
        return new RedirectView("http://localhost:8080/cars");
    }
}
