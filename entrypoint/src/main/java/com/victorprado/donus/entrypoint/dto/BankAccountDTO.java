package com.victorprado.donus.entrypoint.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BankAccountDTO {

    private String id;
    private String number;
    private CustomerDTO customer;
    private BigDecimal balance;
    private boolean deleted;
}
