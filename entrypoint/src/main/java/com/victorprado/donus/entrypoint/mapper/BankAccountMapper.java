package com.victorprado.donus.entrypoint.mapper;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.entrypoint.dto.BankAccountDTO;

public final class BankAccountMapper {

    private BankAccountMapper() {

    }

    public static BankAccount toEntity(BankAccountDTO dto) {
        BankAccount bankAccount = null;
        if (dto != null) {
            bankAccount = BankAccount.builder()
                .balance(dto.getBalance())
                .number(dto.getNumber())
                .customer(CustomerMapper.toEntity(dto.getCustomer()))
                .build();
        }
        return bankAccount;
    }

    public static BankAccountDTO toDto(BankAccount account) {
        BankAccountDTO dto = null;
        if (account != null) {
            dto = BankAccountDTO.builder()
                .balance(account.getBalance())
                .number(account.getNumber())
                .customer(CustomerMapper.toDto(account.getCustomer()))
                .build();
        }
        return dto;
    }
}
