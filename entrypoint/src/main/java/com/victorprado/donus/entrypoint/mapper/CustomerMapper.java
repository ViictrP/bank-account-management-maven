package com.victorprado.donus.entrypoint.mapper;

import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.entrypoint.dto.CustomerDTO;

public final class CustomerMapper {

    private CustomerMapper() {

    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = null;
        if (dto != null) {
            customer = Customer.builder()
                .cpf(dto.getCpf())
                .id(dto.getId())
                .name(dto.getName())
                .build();
        }
        return customer;
    }

    public static CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = null;
        if (customer != null) {
            dto = CustomerDTO.builder()
                .cpf(customer.getCpf())
                .id(customer.getId())
                .name(customer.getName())
                .build();
        }
        return dto;
    }
}
