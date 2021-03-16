package com.victorprado.donus.core.repository;

import com.victorprado.donus.core.entity.BankTransaction;

public interface SaveTransactionRepository {

    BankTransaction save(BankTransaction transaction);
}
