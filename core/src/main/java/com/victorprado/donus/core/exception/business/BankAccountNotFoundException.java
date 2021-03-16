package com.victorprado.donus.core.exception.business;

import com.victorprado.donus.core.exception.CoreException;

public class BankAccountNotFoundException extends CoreException {

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
