package com.timkin.models.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User with such login was not found");
    }
}
