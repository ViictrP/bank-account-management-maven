package com.victorprado.donus.core.usecase.createaccount.impl;

import com.victorprado.donus.core.condition.CheckNotNull;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.business.CustomerHaveAccountException;
import com.victorprado.donus.core.exception.business.CustomerNotFoundException;
import com.victorprado.donus.core.repository.CreateAccountRepository;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.GetCustomerRepository;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final CreateAccountRepository repository;
    private final GetBankAccountRepository getBankAccountRepository;
    private final GetCustomerRepository getCustomerRepository;

    public CreateAccountUseCaseImpl(CreateAccountRepository createAccountRepository,
        GetBankAccountRepository getBankAccountRepository,
        GetCustomerRepository getCustomerRepository) {
        this.repository = createAccountRepository;
        this.getBankAccountRepository = getBankAccountRepository;
        this.getCustomerRepository = getCustomerRepository;
    }

    @Override
    public BankAccount create(Customer customer) {
        log.info("verifying customer data. Customer {}", customer);
        CheckNotNull.check(customer, "The customer data provided is null");
        customer.validate();

        log.info("searching for the customer {}", customer.getCpf());
        Customer customerEntity = getCustomerRepository.get(customer.getCpf())
            .orElseThrow(CustomerNotFoundException::new);

        log.info("validating if customer already has a bank account");
        getBankAccountRepository.get(customerEntity.getId()).ifPresent(entity -> {
            log.error("The customer already have an account. Customer {}", entity);
            throw new CustomerHaveAccountException();
        });

        log.info("creating bank account. Customer {}", customer);
        return createAccount(customer);
    }

    private BankAccount createAccount(Customer customer) {
        return repository.create(
            BankAccount.builder()
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .number(UUID.randomUUID().toString().substring(0, 10))
                .build()
        );
    }
}
