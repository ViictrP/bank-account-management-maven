package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.entity.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction extends Creatable {

    private String id;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private TransactionType type;
    private BigDecimal value;
    private LocalDateTime when;
}
