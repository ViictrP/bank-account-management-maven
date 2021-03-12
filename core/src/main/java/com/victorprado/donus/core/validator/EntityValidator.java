package com.victorprado.donus.core.validator;

import com.victorprado.donus.core.exception.entity.InvalidEntityException;

public interface EntityValidator {

    void validate() throws InvalidEntityException;
}
