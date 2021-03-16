package com.victorprado.donus.core.usecase.banktransaction.impl;

import static com.victorprado.donus.core.entity.enums.TransactionType.TRANSFER;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.exception.business.BankAccountNotFoundException;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.TransferUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

// TODO criar GetBankAccountRepository
// TODO criar UpdateBankAccountRepository
// TODO criar SaveTransactionRepository
// TODO criar ReduceBalance na classe BankTransaction
// TODO criar IncreaseBalance na classe BankTransaction
@Slf4j
public class TransferUseCaseImpl implements TransferUseCase {

    private final GetBankAccountRepository repository;

    public TransferUseCaseImpl(GetBankAccountRepository bankAccountRepository) {
        this.repository = bankAccountRepository;
    }

    @Override
    public BankTransaction transfer(String sourceAccountNumber, String destinationAccountNumber,
        BigDecimal value) {
        log.info("obtaining the source account {}", sourceAccountNumber);
        BankAccount sourceAccount = getBankAccount.getAccountByNumber(sourceAccountNumber)
            .orElseThrow(BankAccountNotFoundException::new);

        log.info("obtaining the destination account {}", destinationAccountNumber);
        BankAccount destinationAccount = getBankAccount.getAccountByNumber(destinationAccountNumber)
            .orElseThrow(BankAccountNotFoundException::new);

        log.info("getting the value from source account {}", sourceAccountNumber);
        sourceAccount.reduceBalance(value);

        log.info("putting the value into destination account {}", destinationAccountNumber);
        destinationAccount.increaseBalance(value);

        BankTransaction transaction = BankTransaction.builder()
            .sourceAccount(sourceAccount)
            .destinationAccount(destinationAccount)
            .value(value)
            .type(TRANSFER)
            .when(LocalDateTime.now())
            .build();

        log.info("updating source account balance {}", sourceAccountNumber);
        updateBankAccountBalance.updateBalance(sourceAccount, sourceAccount.getBalance());

        log.info("updating destination account balance {}", destinationAccountNumber);
        updateBankAccountBalance.updateBalance(destinationAccount, destinationAccount.getBalance());

        log.info(SAVING_TRANSACTION_LOG_MESSAGE, transaction.getId());
        saveTransaction.saveTransaction(transaction);

        return transaction;
    }
}
