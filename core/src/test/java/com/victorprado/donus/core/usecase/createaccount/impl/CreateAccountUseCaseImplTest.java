package com.victorprado.donus.core.usecase.createaccount.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.NullObjectException;
import com.victorprado.donus.core.exception.createaccount.CustomerHaveAccountException;
import com.victorprado.donus.core.exception.createaccount.CustomerNotFoundException;
import com.victorprado.donus.core.exception.createaccount.InvalidCustomerException;
import com.victorprado.donus.core.repository.CreateAccountRepository;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.GetCustomerRepository;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateAccountUseCaseImplTest {

    CreateAccountRepository repository = Mockito.mock(CreateAccountRepository.class);
    GetBankAccountRepository getBankAccountRepository = Mockito.mock(GetBankAccountRepository.class);
    GetCustomerRepository getCustomerRepository = Mockito.mock(GetCustomerRepository.class);

    CreateAccountUseCase useCase = new CreateAccountUseCaseImpl(
        repository,
        getBankAccountRepository,
        getCustomerRepository);

    static final BankAccount BANK_ACCOUNT = BankAccount.builder().build();
    static final String CPF_WITHOUT_ACCOUNT = "withoutAccount";
    static final String CPF_WITH_ACCOUNT = "withAccount";

    @Test
    void givenValidCustomerThenCreateABankAccount() {
        given(repository.create(any(BankAccount.class)))
            .willReturn(BANK_ACCOUNT);

        given(getCustomerRepository.get(CPF_WITHOUT_ACCOUNT))
            .willReturn(Optional.of(new Customer("id", "name", CPF_WITHOUT_ACCOUNT)));

        given(getBankAccountRepository.get(anyString()))
            .willReturn(Optional.empty());

        BankAccount bankAccount = useCase.create(new Customer("jfsgdsfd", "Victor Prado", CPF_WITHOUT_ACCOUNT));

        assertThat(bankAccount).isEqualTo(BANK_ACCOUNT);
    }

    @Test
    void givenInvalidCustomerDataThenThrowInvalidCustomerDataException() {
        thenThrownBy(() -> useCase.create(new Customer()))
            .isInstanceOf(InvalidCustomerException.class)
            .hasMessage("Invalid customer data provided");

    }

    @Test
    void givenNullCustomerThenThrowNullObjectException() {
        thenThrownBy(() -> useCase.create(null))
            .isInstanceOf(NullObjectException.class)
            .hasMessage("The customer data provided is null");
    }

    @Test
    void givenCustomerWithAccountThenThrowCustomerHaveAccountException() {
        given(repository.create(any(BankAccount.class)))
            .willReturn(BANK_ACCOUNT);

        given(getCustomerRepository.get(CPF_WITH_ACCOUNT))
            .willReturn(Optional.of(new Customer("id", "name", CPF_WITH_ACCOUNT)));

        given(getBankAccountRepository.get(anyString()))
            .willReturn(Optional.of(BankAccount.builder().build()));

        thenThrownBy(() -> useCase.create(new Customer("jfsgdsfd", "Victor Prado", CPF_WITH_ACCOUNT)))
            .isInstanceOf(CustomerHaveAccountException.class)
            .hasMessage("The customer already have an account");
    }

    @Test
    void givenNotExistingCustomerThenThrowCustomerNotFoundException() {
        given(getCustomerRepository.get(anyString()))
            .willReturn(Optional.empty());

        thenThrownBy(() -> useCase.create(new Customer("jfsgdsfd", "Victor Prado", CPF_WITH_ACCOUNT)))
            .isInstanceOf(CustomerNotFoundException.class)
            .hasMessage("The customer does not exist");
    }
}
