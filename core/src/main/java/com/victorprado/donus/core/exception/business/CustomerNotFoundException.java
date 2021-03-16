package com.victorprado.donus.core.exception.business;

import com.victorprado.donus.core.exception.CoreException;

public class CustomerNotFoundException extends CoreException {

    public CustomerNotFoundException() {
        super("The customer does not exist");
    }
}
