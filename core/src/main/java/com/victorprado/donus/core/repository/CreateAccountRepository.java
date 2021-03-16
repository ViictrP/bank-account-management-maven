package com.victorprado.donus.core.repository;

import com.victorprado.donus.core.entity.BankAccount;

public interface CreateAccountRepository {

    BankAccount createBankAccount(BankAccount account);
}
