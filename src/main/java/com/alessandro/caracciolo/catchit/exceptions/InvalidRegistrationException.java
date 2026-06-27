package com.alessandro.caracciolo.catchit.exceptions;

public class InvalidRegistrationException extends Exception {
    public InvalidRegistrationException(String message) {
        super("Error: registration text fields are not valid! \nDetails: " + message);
    }
}
