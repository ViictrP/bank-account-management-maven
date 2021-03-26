package com.victorprado.donus.entrypoint.mapper;

import static org.assertj.core.api.BDDAssertions.then;

import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.entrypoint.dto.CustomerDTO;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {

    @Test
    void givenValidCustomerWhenConvertingToDTOThenReturnConvertedCustomer() {
        Customer customer = Customer.builder()
            .cpf("00000000000")
            .name("Victor Prado")
            .id("21343")
            .build();

        CustomerDTO dto = CustomerMapper.toDto(customer);

        then(dto).isNotNull();
        then(dto.getCpf()).isEqualTo(customer.getCpf());
        then(dto.getId()).isEqualTo(customer.getId());
        then(dto.getName()).isEqualTo(customer.getName());
    }

    @Test
    void givenNullCustomerWhenConvertingToDTOThenReturnNull() {
        CustomerDTO dto = CustomerMapper.toDto(null);
        then(dto).isNull();
    }

    @Test
    void givenValidCustomerWhenConvertingToEntityThenReturnConvertedCustomer() {
        CustomerDTO dto = CustomerDTO.builder()
            .cpf("00000000000")
            .name("Victor Prado")
            .id("21343")
            .build();

        Customer customer = CustomerMapper.toEntity(dto);

        then(dto).isNotNull();
        then(dto.getCpf()).isEqualTo(customer.getCpf());
        then(dto.getId()).isEqualTo(customer.getId());
        then(dto.getName()).isEqualTo(customer.getName());
    }

    @Test
    void givenNullCustomerWhenConvertingToEntityThenReturnNull() {
        Customer customer = CustomerMapper.toEntity(null);
        then(customer).isNull();
    }
}
