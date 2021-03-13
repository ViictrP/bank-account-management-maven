package com.victorprado.donus.core.exception.createaccount;

import com.victorprado.donus.core.exception.CoreException;

public class CustomerHaveAccountException extends CoreException {

    public CustomerHaveAccountException() {
        super("The customer already have an account");
    }
}
