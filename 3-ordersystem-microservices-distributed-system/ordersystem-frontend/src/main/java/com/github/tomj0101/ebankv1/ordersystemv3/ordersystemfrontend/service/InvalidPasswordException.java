package com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.service;

public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Incorrect password");
    }
}
