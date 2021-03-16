package com.victorprado.donus.core.usecase.banktransaction.impl;

import static com.victorprado.donus.core.entity.enums.TransactionType.TRANSFER;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.exception.business.BankAccountNotFoundException;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.SaveTransactionRepository;
import com.victorprado.donus.core.repository.UpdateBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.TransferUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferUseCaseImpl implements TransferUseCase {

    private final GetBankAccountRepository getAccountRepository;
    private final UpdateBankAccountRepository updateBankAccountRepository;
    private final SaveTransactionRepository saveTransactionRepository;

    public TransferUseCaseImpl(GetBankAccountRepository bankAccountRepository,
        UpdateBankAccountRepository updateBankAccountRepository,
        SaveTransactionRepository saveTransactionRepository) {
        this.getAccountRepository = bankAccountRepository;
        this.updateBankAccountRepository = updateBankAccountRepository;
        this.saveTransactionRepository = saveTransactionRepository;
    }

    @Override
    public BankTransaction transfer(String sourceAccountNumber, String destinationAccountNumber,
        BigDecimal value) {
        log.info("obtaining the source account {}", sourceAccountNumber);
        BankAccount sourceAccount = getAccountRepository.get(sourceAccountNumber)
            .orElseThrow(() -> new BankAccountNotFoundException("The bank account " + sourceAccountNumber + " does not exist."));

        log.info("obtaining the destination account {}", destinationAccountNumber);
        BankAccount destinationAccount = getAccountRepository.get(destinationAccountNumber)
            .orElseThrow(() -> new BankAccountNotFoundException("The bank account " + destinationAccountNumber + " does not exist"));

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
        updateBankAccountRepository.update(sourceAccount);

        log.info("updating destination account balance {}", destinationAccountNumber);
        updateBankAccountRepository.update(destinationAccount);

        log.info("saving the transaction. Transaction {}", transaction);
        saveTransactionRepository.save(transaction);

        return transaction;
    }
}
