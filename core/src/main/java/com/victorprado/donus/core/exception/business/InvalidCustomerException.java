package com.victorprado.donus.core.exception.business;

import com.victorprado.donus.core.exception.entity.InvalidEntityException;

public class InvalidCustomerException extends InvalidEntityException {

    public InvalidCustomerException() {
        super("Invalid customer data provided");
    }
}
