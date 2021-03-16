package com.victorprado.donus.core.usecase.banktransaction;

import com.victorprado.donus.core.entity.BankTransaction;
import java.math.BigDecimal;

public interface TransferUseCase {

    BankTransaction transfer(String sourceAccountNumber, String destinationAccountNumber,
        BigDecimal value);
}
