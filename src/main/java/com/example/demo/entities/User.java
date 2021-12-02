package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String email;
    private String password;
    private String name;
    private int age;
    private double salary;
    private Role authority;
    private List<Car> cars = new ArrayList<>();

    public User(String email, String password, String name, double salary, int age, Role authority) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.authority = authority;
    }

    public User() {

    }

    @Size(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(value = 18)
    @Max(value = 100)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Min(value = 3)
    @Max(value = 30000)
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getAuthority() {
        return authority;
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }

    @Size(min = 5, max = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Employee{" + ", name='" + this.name + '\'' + ", age='" + this.age + '\'' + ", salary='" + this.salary + '\'' +'}';
    }
}
