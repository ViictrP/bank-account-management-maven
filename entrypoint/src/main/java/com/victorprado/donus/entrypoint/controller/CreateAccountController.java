package com.victorprado.donus.entrypoint.controller;

import static com.victorprado.donus.entrypoint.response.StatusCode.CREATED;
import static com.victorprado.donus.entrypoint.response.StatusCode.UNPROCESSABLE_ENTITY;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.CoreException;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.entrypoint.dto.BankAccountDTO;
import com.victorprado.donus.entrypoint.dto.CustomerDTO;
import com.victorprado.donus.entrypoint.mapper.BankAccountMapper;
import com.victorprado.donus.entrypoint.mapper.CustomerMapper;
import com.victorprado.donus.entrypoint.request.EntryPointRequest;
import com.victorprado.donus.entrypoint.response.EntryPointResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAccountController {

    private final CreateAccountUseCase useCase;

    public CreateAccountController(CreateAccountUseCase useCase) {
        this.useCase = useCase;
    }

    public EntryPointResponse<BankAccountDTO> createAccount(EntryPointRequest<CustomerDTO> request) {
        try {
            log.info("Obtaining the request data {}", request.getData());
            Customer customer = CustomerMapper.toEntity(request.getData());
            log.info("creating the bank account. Customer {}", customer);
            BankAccount account = useCase.create(customer);
            log.info("mapping entity to dto.");
            BankAccountDTO dto = BankAccountMapper.toDto(account);
            log.info("account created. Dto {}", dto);
            return new EntryPointResponse<>(dto, CREATED);
        } catch (CoreException exception) {
            return new EntryPointResponse<>(null, exception.getMessage(), UNPROCESSABLE_ENTITY);
        }
    }
}
