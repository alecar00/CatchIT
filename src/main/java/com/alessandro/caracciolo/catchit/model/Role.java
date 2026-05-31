package com.alessandro.caracciolo.catchit.model;

public enum Role {
    RESTAURANT(1),
    RIDER(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
