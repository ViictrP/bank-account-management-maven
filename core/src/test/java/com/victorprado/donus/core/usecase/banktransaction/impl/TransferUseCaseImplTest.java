package com.victorprado.donus.core.usecase.banktransaction.impl;

import static com.victorprado.donus.core.entity.enums.TransactionType.TRANSFER;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.exception.business.BankAccountNotFoundException;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.SaveTransactionRepository;
import com.victorprado.donus.core.repository.UpdateBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.TransferUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TransferUseCaseImplTest {

    UpdateBankAccountRepository updateBankAccountRepository = mock(
        UpdateBankAccountRepository.class);
    SaveTransactionRepository saveTransactionRepository = mock(SaveTransactionRepository.class);
    GetBankAccountRepository bankAccountRepository = mock(GetBankAccountRepository.class);
    TransferUseCase useCase = new TransferUseCaseImpl(bankAccountRepository,
        updateBankAccountRepository, saveTransactionRepository);

    @Test
    void givenValidAccountsThenShouldTransferWithSuccess() {
        BankAccount account_1 = BankAccount.builder().balance(BigDecimal.valueOf(100)).build();
        BankAccount account_2 = BankAccount.builder().balance(BigDecimal.valueOf(100)).build();

        when(bankAccountRepository.getBankAccount("1234")).thenReturn(Optional.of(account_1));
        when(bankAccountRepository.getBankAccount("5678")).thenReturn(Optional.of(account_2));

        when(saveTransactionRepository.saveTransaction(any(BankTransaction.class)))
            .thenReturn(BankTransaction.builder()
                .value(BigDecimal.valueOf(100))
                .when(LocalDateTime.now())
                .destinationAccount(account_2)
                .sourceAccount(account_1)
                .type(TRANSFER)
                .build());

        BankTransaction transaction = useCase.transfer("1234", "5678", BigDecimal.valueOf(100));

        then(transaction).isNotNull();
        then(transaction.getValue()).isEqualTo(BigDecimal.valueOf(100));
        then(transaction.getType()).isEqualTo(TRANSFER);
        then(transaction.getDestinationAccount()).isEqualTo(account_2);
        then(transaction.getSourceAccount()).isEqualTo(account_1);
        then(transaction.getWhen()).isNotNull();
        then(account_1.getBalance()).isEqualTo(BigDecimal.ZERO);
        then(account_2.getBalance()).isEqualTo(BigDecimal.valueOf(200));
    }

    @Test
    void givenNonexistentSourceAccountThenThrowBankAccountNotFoundException() {
        when(bankAccountRepository.getBankAccount("1234"))
            .thenThrow(new BankAccountNotFoundException("The bank account 1234 does not exist."));

        thenThrownBy(() -> useCase.transfer("1234", "5678", BigDecimal.ONE))
            .hasMessage("The bank account 1234 does not exist.");
    }

    @Test
    void givenNonexistentDestinationAccountThenThrowBankAccountNotFoundException() {
        BankAccount account = BankAccount.builder().balance(BigDecimal.valueOf(100)).build();
        when(bankAccountRepository.getBankAccount("1234")).thenReturn(Optional.of(account));
        when(bankAccountRepository.getBankAccount("5678"))
            .thenThrow(new BankAccountNotFoundException("The bank account 5678 does not exist."));

        thenThrownBy(() -> useCase.transfer("1234", "5678", BigDecimal.ONE))
            .hasMessage("The bank account 5678 does not exist.");
    }
}
