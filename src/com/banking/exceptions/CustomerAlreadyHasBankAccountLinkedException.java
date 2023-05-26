package com.banking.exceptions;

public class CustomerAlreadyHasBankAccountLinkedException extends Exception {
    public CustomerAlreadyHasBankAccountLinkedException(String message) {
        super(message);
    }
}
