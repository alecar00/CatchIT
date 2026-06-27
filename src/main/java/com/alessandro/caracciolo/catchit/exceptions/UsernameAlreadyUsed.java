package com.alessandro.caracciolo.catchit.exceptions;

public class UsernameAlreadyUsed extends Exception {
    public UsernameAlreadyUsed() {
        super("Username already used!");
    }
}
