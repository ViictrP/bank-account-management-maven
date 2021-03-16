package com.victorprado.donus.core.exception.business;

import com.victorprado.donus.core.exception.CoreException;

public class CustomerHaveAccountException extends CoreException {

    public CustomerHaveAccountException() {
        super("The customer already have an account");
    }
}
