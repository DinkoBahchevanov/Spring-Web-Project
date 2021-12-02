package com.example.demo.controllers;

import com.example.demo.entities.Car;
import com.example.demo.entities.Service;
import com.example.demo.entities.User;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.ServiceRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
public class ServiceController {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public ServiceController(CarRepository carRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    @GetMapping("/services")
    public ModelAndView showAllCars(Model model) {
        Service service = new Service();
        model.addAttribute("service",service);
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return new ModelAndView("services.html");
    }

    @PostMapping("/add-service")
    public RedirectView addService(Service service) {
        try {
            serviceRepository.save(service);
        }catch (javax.validation.ConstraintViolationException ConstraintViolationException) {
            return new RedirectView("http://localhost:8080/add-service");
        }
        return new RedirectView("http://localhost:8080/cars");
    }

    @GetMapping("/add-service")
    public ModelAndView getAddService(Service service) {
        return new ModelAndView("add-service.html");
    }


    @GetMapping("/service/delete/{id}")
    public RedirectView deleteService(@PathVariable("id") int id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service Id:" + id));
        serviceRepository.delete(service);
        System.out.println("deleted");
        return new RedirectView("http://localhost:8080/services");
    }

    @PostMapping("/add-car-to-service/{id}")
    public RedirectView addCarToService(@PathVariable("id") int id, Car car) {
        Service byName = serviceRepository.findByName(car.getService().getName());
        if (byName != null) {
            Optional<Car> byId = carRepository.findById(id);
            byId.get().setService(byName);
            byName.getCars().add(byId.get());
            carRepository.save(byId.get());
            serviceRepository.save(byName);
        } else return new RedirectView("http://localhost:8080/add-car-service/"+id);
        return new RedirectView("http://localhost:8080/users_details");
    }

    @GetMapping("/add-car-service/{id}")
    public ModelAndView showAddCarToServiceForm(@PathVariable("id") int id, Model model) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("car", car);

        return new ModelAndView("add-car-to-service.html");
    }
}
