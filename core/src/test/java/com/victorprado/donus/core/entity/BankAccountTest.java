package com.victorprado.donus.core.entity;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BankAccountTest {

    @Test
    void givenValidValueThenReduceBalance() {
        BankAccount account = BankAccount.builder().balance(BigDecimal.TEN).build();

        account.reduceBalance(BigDecimal.TEN);

        then(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void givenValidValueThenIncreaseBalance() {
        BankAccount account = BankAccount.builder().balance(BigDecimal.TEN).build();

        account.increaseBalance(BigDecimal.TEN);

        then(account.getBalance()).isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void givenNullBalanceThenSetValueAsBalance() {
        BankAccount account = BankAccount.builder().build();

        account.increaseBalance(BigDecimal.TEN);

        then(account.getBalance()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void givenNullBalanceThenThrowInsufficientBalanceException() {
        BankAccount account = BankAccount.builder().build();
        thenThrownBy(() -> account.reduceBalance(BigDecimal.TEN))
            .hasMessage(account.getNumber());
    }
}
