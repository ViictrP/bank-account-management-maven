package com.victorprado.donus.core.usecase.createaccount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.NullObjectException;
import com.victorprado.donus.core.exception.createaccount.InvalidCustomerException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateAccountUseCaseTest {

    CreateAccount createAccount = Mockito.mock(CreateAccount.class);
    CreateAccountUseCase useCase = new CreateAccountUseCase(createAccount);
    static final BankAccount BANK_ACCOUNT = new BankAccount();

    @Test
    void givenValidCustomerThenCreateABankAccount() {
        given(createAccount.create(any(Customer.class)))
            .willReturn(BANK_ACCOUNT);

        BankAccount bankAccount = useCase.create(new Customer("jfsgdsfd", "Victor Prado", "00000000000"));

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
}
