package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;

public interface CreateAccountUseCase {

    BankAccount create(Customer customer);
}
