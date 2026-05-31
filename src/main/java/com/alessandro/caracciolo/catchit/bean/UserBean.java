package com.alessandro.caracciolo.catchit.bean;

import com.alessandro.caracciolo.catchit.model.Role;

public class UserBean {
    private String username;
    private String password;
    private final String role;

    public UserBean(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
