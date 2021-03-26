package com.victorprado.donus.entrypoint.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.business.InvalidCustomerException;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.entrypoint.dto.BankAccountDTO;
import com.victorprado.donus.entrypoint.dto.CustomerDTO;
import com.victorprado.donus.entrypoint.mapper.CustomerMapper;
import com.victorprado.donus.entrypoint.request.EntryPointRequest;
import com.victorprado.donus.entrypoint.response.EntryPointResponse;
import com.victorprado.donus.entrypoint.response.StatusCode;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateAccountControllerTest {

    final CreateAccountUseCase useCase = Mockito.mock(CreateAccountUseCase.class);
    final CreateAccountController controller = new CreateAccountController(useCase);

    final CustomerDTO customer = CustomerDTO.builder()
        .name("Victor Prado")
        .id("12342")
        .cpf("00000000000")
        .build();

    @Test
    void givenValidCustomerWhenCreatingAccountThenReturnAccountCreated() {
        BankAccount account = BankAccount.builder()
            .balance(BigDecimal.TEN)
            .customer(CustomerMapper.toEntity(customer))
            .number("1234")
            .id("1234")
            .deleted(false)
            .build();

        given(useCase.create(any(Customer.class))).willReturn(account);

        EntryPointRequest<CustomerDTO> request = new EntryPointRequest<>(customer);
        EntryPointResponse<BankAccountDTO> dto = controller.createAccount(request);

        then(dto).isNotNull();
        then(dto.getData().getBalance()).isEqualTo(account.getBalance());
        then(dto.getData().getNumber()).isEqualTo(account.getNumber());
        then(dto.getData().getCustomer().equals(customer)).isTrue();
        then(dto.getData().isDeleted()).isEqualTo(account.isDeleted());
    }

    @Test
    void givenInvalidCustomerThenReturnBadRequest() {
        given(useCase.create(any(Customer.class))).willThrow(new InvalidCustomerException());

        EntryPointRequest<CustomerDTO> request = new EntryPointRequest<>(customer);
        EntryPointResponse<BankAccountDTO> dto = controller.createAccount(request);

        then(dto.getData()).isNull();
        then(dto.getMessage()).isNotNull();
        then(dto.getCode()).isEqualTo(StatusCode.UNPROCESSABLE_ENTITY);
    }
}
