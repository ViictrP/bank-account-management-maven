package com.victorprado.donus.core.usecase.banktransaction.impl;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.enums.TransactionType;
import com.victorprado.donus.core.exception.business.BankAccountNotFoundException;
import com.victorprado.donus.core.repository.GetBankAccountRepository;
import com.victorprado.donus.core.repository.SaveTransactionRepository;
import com.victorprado.donus.core.repository.UpdateBankAccountRepository;
import com.victorprado.donus.core.usecase.banktransaction.DepositUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DepositUseCaseImpl implements DepositUseCase {

    public static final BigDecimal BONUS = BigDecimal.valueOf(0.005);

    private final GetBankAccountRepository getBankAccountRepository;
    private final UpdateBankAccountRepository updateBankAccountRepository;
    private final SaveTransactionRepository saveTransactionRepository;

    public DepositUseCaseImpl(
        GetBankAccountRepository getBankAccountRepository,
        UpdateBankAccountRepository updateBankAccountRepository,
        SaveTransactionRepository saveTransactionRepository) {
        this.getBankAccountRepository = getBankAccountRepository;
        this.updateBankAccountRepository = updateBankAccountRepository;
        this.saveTransactionRepository = saveTransactionRepository;
    }

    public BankTransaction deposit(String accountNumber, BigDecimal depositValue) {
        log.info("obtaining the account {}", accountNumber);
        BankAccount account = getBankAccountRepository.get(accountNumber)
            .orElseThrow(() -> new BankAccountNotFoundException("The bank account " + accountNumber + " does not exist."));

        log.info("depositing value {} into account {}", depositValue, accountNumber);
        BigDecimal depositWithBonus = depositValue.add(depositValue.multiply(BONUS));
        account.increaseBalance(depositWithBonus);

        log.info("updating account balance {}", accountNumber);
        updateBankAccountRepository.update(account);

        BankTransaction transaction = BankTransaction.builder()
            .sourceAccount(account)
            .value(depositValue)
            .type(TransactionType.DEPOSIT)
            .when(LocalDateTime.now())
            .build();

        log.info("saving the transaction. Transaction {}", transaction);
        saveTransactionRepository.save(transaction);

        return transaction;
    }
}
