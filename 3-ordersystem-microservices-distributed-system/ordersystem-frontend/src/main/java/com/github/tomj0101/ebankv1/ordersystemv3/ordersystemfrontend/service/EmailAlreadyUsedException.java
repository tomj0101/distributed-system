package com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.service;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}
