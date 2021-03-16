package com.victorprado.donus.core.usecase.banktransaction.impl;

import static com.victorprado.donus.core.entity.enums.TransactionType.TRANSFER;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.TransferUseCase;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TransferUseCaseImplTest {

    GetBankAccountRepository bankAccountRepository = mock(GetBankAccountRepository.class);
    TransferUseCase useCase = new TransferUseCaseImpl(bankAccountRepository);

    final static BankAccount ACCOUNT_1 = BankAccount.builder().balance(BigDecimal.valueOf(100))
        .build();
    final static BankAccount ACCOUNT_2 = BankAccount.builder().balance(BigDecimal.valueOf(100))
        .build();

    @Test
    void givenValidAccountsThenShouldTransferWithSuccess() {
        when(bankAccountRepository.get("1234")).thenReturn(Optional.of(ACCOUNT_1));
        when(bankAccountRepository.get("5678")).thenReturn(Optional.of(ACCOUNT_2));

        BankTransaction transaction = useCase.transfer("1234", "5678", BigDecimal.valueOf(100));

        then(transaction).isNotNull();
        then(transaction.getValue()).isEqualTo(BigDecimal.valueOf(100));
        then(transaction.getType()).isEqualTo(TRANSFER);
        then(transaction.getDestinationAccount()).isEqualTo("5678");
        then(transaction.getSourceAccount()).isEqualTo("1234");
        then(transaction.getWhen()).isNotNull();
    }
}
