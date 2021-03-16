package com.victorprado.donus.core.repository;

import com.victorprado.donus.core.entity.BankAccount;
import java.util.Optional;

public interface GetBankAccountRepository {

    Optional<BankAccount> getBankAccount(String customerId);
}
