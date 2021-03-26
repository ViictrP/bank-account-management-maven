package com.victorprado.donus.configuration;

import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.entrypoint.controller.CreateAccountController;

public class EntryPointConfiguration {

    public CreateAccountController createAccountController(CreateAccountUseCase useCase) {
        return new CreateAccountController(useCase);
    }
}
