package com.alessandro.caracciolo.catchit.model;


public class User {
    private final String username;
    private final String password;
    private final Role role;

    // Costruttore completo (usato dal DB e dal Memory DAO)
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Costruttore parziale (può servire se vuoi creare un utente senza ruolo per qualche verifica)
    public User(String username, String password) {
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

    public Role getRole() {
        return role;
    }
}
