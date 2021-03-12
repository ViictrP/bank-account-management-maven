package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.CheckNotNull;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAccountUseCase {

    private final CreateAccount createAccount;

    public CreateAccountUseCase(CreateAccount createAccount) {
        this.createAccount = createAccount;
    }

    public BankAccount create(Customer customer) {
        log.info("verifying customer data. Customer {}", customer);
        CheckNotNull.check(customer, "The customer data provided is null");
        customer.validate();
        log.info("creating bank account. Customer {}", customer);
        return createAccount.create(customer);
    }
}
