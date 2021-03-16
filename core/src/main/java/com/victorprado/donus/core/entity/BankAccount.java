package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.exception.business.InsufficientBankAccountBalanceException;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankAccount extends Upgradeable {

    private String id;
    private String number;
    private Customer customer;
    private BigDecimal balance;
    private boolean deleted;

    public void increaseBalance(BigDecimal value) {
        if (this.balance == null) {
            this.balance = value;
        } else {
            this.balance = this.balance.add(value);
        }
    }

    public void reduceBalance(BigDecimal value) {
        if (this.balance != null && this.balance.compareTo(value) >= 0) {
            this.balance = this.balance.subtract(value);
            return;
        }
        throw new InsufficientBankAccountBalanceException(this.number);
    }
}
