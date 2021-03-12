package com.victorprado.donus.core.exception.entity;

import com.victorprado.donus.core.exception.CoreException;

public class InvalidEntityException extends CoreException {

    public InvalidEntityException(String message) {
        super(message);
    }
}
