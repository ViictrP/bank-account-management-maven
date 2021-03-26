package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.exception.business.InvalidCustomerException;
import com.victorprado.donus.core.exception.entity.InvalidEntityException;
import com.victorprado.donus.core.entity.validator.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Upgradeable implements EntityValidator {

    private String id;
    private String name;
    private String cpf;

    @Override
    public void validate() throws InvalidEntityException {
        if (StringUtils.isEmpty(this.cpf) && StringUtils.isEmpty(this.name)) {
            throw new InvalidCustomerException();
        }
    }
}
