package com.victorprado.donus.core.usecase.banktransaction.impl;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WithdrawUseCaseImpl implements WithdrawUseCase {

    public static final BigDecimal TAX = BigDecimal.valueOf(0.01);

    private final GetBankAccountRepository getBankAccountRepository;
    private final UpdateBankAccountRepository updateBankAccountRepository;
    private final SaveTransactionRepository saveTransactionRepository;

    public WithdrawUseCaseImpl(
        GetBankAccountRepository getBankAccountRepository,
        UpdateBankAccountRepository updateBankAccountRepository,
        SaveTransactionRepository saveTransactionRepository) {
        this.getBankAccountRepository = getBankAccountRepository;
        this.updateBankAccountRepository = updateBankAccountRepository;
        this.saveTransactionRepository = saveTransactionRepository;
    }

    @Override
    public BankTransaction withdraw(String accountNumber, BigDecimal value) {
        log.info("obtaining the account {}", accountNumber);
        BankAccount account = getBankAccountRepository.get(accountNumber)
            .orElseThrow(() -> new BankAccountNotFoundException("The bank account " + accountNumber + " does not exist."));

        log.info("withdrawing value {} from account {}", value, accountNumber);
        BigDecimal valueWithTax = value.add(value.multiply(TAX));
        account.reduceBalance(valueWithTax);

        log.info("updating account balance {}", accountNumber);
        updateBankAccountRepository.update(account);

        BankTransaction transaction = BankTransaction.builder()
            .sourceAccount(account)
            .value(value)
            .type(TransactionType.WITHDRAW)
            .when(LocalDateTime.now())
            .build();

        log.info("saving the transaction. Transaction {}", transaction);
        saveTransactionRepository.save(transaction);

        return transaction;
    }
}
