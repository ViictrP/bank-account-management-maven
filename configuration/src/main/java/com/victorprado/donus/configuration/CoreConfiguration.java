package com.victorprado.donus.configuration;

import com.victorprado.donus.core.repository.CreateAccountRepository;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.GetCustomerRepository;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.impl.CreateAccountUseCaseImpl;

public class CoreConfiguration {

    public CreateAccountUseCase createAccountUseCase(CreateAccountRepository repository,
        GetCustomerRepository getCustomerRepository,
        GetBankAccountRepository getBankAccountRepository) {
        return new CreateAccountUseCaseImpl(repository, getBankAccountRepository, getCustomerRepository);
    }
}
