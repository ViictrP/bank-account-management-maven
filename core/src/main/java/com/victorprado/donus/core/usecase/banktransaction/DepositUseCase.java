package com.victorprado.donus.core.usecase.banktransaction;

import com.victorprado.donus.core.entity.BankTransaction;
import java.math.BigDecimal;

public interface DepositUseCase {

    BankTransaction deposit(String accountNumber, BigDecimal value);
}
