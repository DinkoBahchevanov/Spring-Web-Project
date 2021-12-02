package com.example.demo.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Access(AccessType.FIELD)
public class Role implements GrantedAuthority {

    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
