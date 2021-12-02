package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String brand;
    private String model;
    private int doors;
    private LocalDateTime dateTime;
    private User user;
    private Service service;

    public Car() {
    }

    public Car(String brand, String model, int doors, LocalDateTime dateTime) {
        this.brand = brand;
        this.model = model;
        this.doors = doors;
        this.dateTime = dateTime;
    }

    public Car(String brand, String model, int doors, LocalDateTime dateTime, User user) {
        this.brand = brand;
        this.model = model;
        this.doors = doors;
        this.dateTime = dateTime;
        this.user = user;
    }

    @Size(min = 3, max = 15)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Size(min = 3, max = 15)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Min(value = 2)
    @Max(value = 5)
    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "service_id")
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
