package com.example.demo.entities;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "services")
public class Service extends BaseEntity {

    private String name;
    private String street;
    private int rating;
    private List<Car> cars;

    public Service() {

    }

    @NotNull
    @Size(min = 2, max = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = 2, max = 10)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Min(value = 0)
    @Max(value = 5)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @OneToMany(mappedBy = "service")
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Service(String name, String street, int rating) {
        this.name = name;
        this.street = street;
        this.rating = rating;
    }

    public Service(String name, String street, int rating, List<Car> cars) {
        this.name = name;
        this.street = street;
        this.rating = rating;
        this.cars = cars;
    }
}
