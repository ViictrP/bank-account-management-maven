package com.victorprado.donus.core.entity;

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
}
