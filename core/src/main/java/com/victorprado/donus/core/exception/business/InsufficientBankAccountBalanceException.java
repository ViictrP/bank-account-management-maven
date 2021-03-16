package com.victorprado.donus.core.exception.business;

import com.victorprado.donus.core.exception.CoreException;

public class InsufficientBankAccountBalanceException extends CoreException {

    public InsufficientBankAccountBalanceException(String message) {
        super(message);
    }
}
