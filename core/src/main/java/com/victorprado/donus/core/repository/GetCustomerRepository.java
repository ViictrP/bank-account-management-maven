package com.victorprado.donus.core.repository;

import com.victorprado.donus.core.entity.Customer;
import java.util.Optional;

public interface GetCustomerRepository {

    Optional<Customer> getCustomer(String cpf);
}
