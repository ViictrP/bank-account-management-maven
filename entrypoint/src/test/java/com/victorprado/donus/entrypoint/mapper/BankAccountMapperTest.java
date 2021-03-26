package com.victorprado.donus.entrypoint.mapper;

import static org.assertj.core.api.BDDAssertions.then;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.entrypoint.dto.BankAccountDTO;
import com.victorprado.donus.entrypoint.dto.CustomerDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BankAccountMapperTest {

    @Test
    void givenValidAccountWhenConvertingToDTOThenReturnConvertedAccount() {
        BankAccount account = BankAccount.builder()
            .customer(Customer.builder().build())
            .id("2314")
            .balance(BigDecimal.TEN)
            .deleted(false)
            .build();

        BankAccountDTO dto = BankAccountMapper.toDto(account);

        then(dto).isNotNull();
        then(dto.getNumber()).isEqualTo(account.getNumber());
        then(dto.getBalance()).isEqualTo(account.getBalance());
        then(dto.getNumber()).isEqualTo(account.getNumber());
        then(dto.getCustomer()).isEqualTo(CustomerMapper.toDto(account.getCustomer()));
    }

    @Test
    void givenNullAccountWhenConvertingToDTOThenReturnNull() {
        BankAccountDTO dto = BankAccountMapper.toDto(null);
        then(dto).isNull();
    }

    @Test
    void givenValidAccountWhenConvertingToEntityThenReturnConvertedAccount() {
        BankAccountDTO account = BankAccountDTO.builder()
            .customer(CustomerDTO.builder().build())
            .id("2314")
            .balance(BigDecimal.TEN)
            .deleted(false)
            .build();

        BankAccount dto = BankAccountMapper.toEntity(account);

        then(dto).isNotNull();
        then(dto.getNumber()).isEqualTo(account.getNumber());
        then(dto.getBalance()).isEqualTo(account.getBalance());
        then(dto.getNumber()).isEqualTo(account.getNumber());
    }

    @Test
    void givenNullAccountWhenConvertingToEntityThenReturnNull() {
        BankAccount dto = BankAccountMapper.toEntity(null);
        then(dto).isNull();
    }
}
