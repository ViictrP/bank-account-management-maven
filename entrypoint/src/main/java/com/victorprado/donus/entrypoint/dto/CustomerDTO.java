package com.victorprado.donus.entrypoint.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String id;
    private String name;
    private String cpf;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerDTO dto = (CustomerDTO) o;
        return Objects.equals(cpf, dto.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
