package com.victorprado.donus.core.usecase.banktransaction.impl;

import static com.victorprado.donus.core.entity.enums.TransactionType.TRANSFER;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.enums.TransactionType;
import com.victorprado.donus.core.exception.business.BankAccountNotFoundException;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.SaveTransactionRepository;
import com.victorprado.donus.core.repository.UpdateBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.WithdrawUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class WithdrawUseCaseImplTest {

    GetBankAccountRepository getBankAccountRepository = mock(GetBankAccountRepository.class);
    UpdateBankAccountRepository updateBankAccountRepository = mock(
        UpdateBankAccountRepository.class);
    SaveTransactionRepository saveTransactionRepository = mock(SaveTransactionRepository.class);

    WithdrawUseCase useCase = new WithdrawUseCaseImpl(getBankAccountRepository,
        updateBankAccountRepository,
        saveTransactionRepository);

    @Test
    void givenExistentAccountThenWithdrawWithSuccess() {
        BankAccount account = BankAccount.builder()
            .balance(BigDecimal.valueOf(100))
            .build();

        when(getBankAccountRepository.get(anyString()))
            .thenReturn(Optional.of(account));

        doNothing().when(updateBankAccountRepository).update(any(BankAccount.class));

        when(saveTransactionRepository.save(any(BankTransaction.class)))
            .thenReturn(BankTransaction.builder()
                .value(BigDecimal.valueOf(100))
                .when(LocalDateTime.now())
                .sourceAccount(account)
                .type(TRANSFER)
                .build());

        BigDecimal valueWithTax = BigDecimal.TEN.add(BigDecimal.TEN.multiply(BigDecimal.valueOf(0.01)));
        BigDecimal balance = BigDecimal.valueOf(100).subtract(valueWithTax);
        BankTransaction transaction = useCase.withdraw("1234", BigDecimal.TEN);

        then(transaction).isNotNull();
        then(transaction.getType()).isEqualTo(TransactionType.WITHDRAW);
        then(transaction.getValue()).isEqualTo(BigDecimal.TEN);
        then(transaction.getSourceAccount()).isEqualTo(account);
        then(transaction.getWhen()).isNotNull();
        then(account.getBalance()).isEqualTo(balance);
    }

    @Test
    void givenNonexistentSourceAccountThenThrowBankAccountNotFoundException() {
        when(getBankAccountRepository.get("1234"))
            .thenThrow(new BankAccountNotFoundException("The bank account 1234 does not exist."));

        thenThrownBy(() -> useCase.withdraw("1234", BigDecimal.ONE))
            .hasMessage("The bank account 1234 does not exist.");
    }
}
