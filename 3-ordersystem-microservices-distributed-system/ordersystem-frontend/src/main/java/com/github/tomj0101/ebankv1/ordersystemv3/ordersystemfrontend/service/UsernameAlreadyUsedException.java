package com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.service;

public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }
}
