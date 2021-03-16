package com.victorprado.donus.core.entity.enums;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;

class TransactionTypeTest {

    @Test
    void givenValidDescriptionThenReturnEnum() {
        String description = "TRANSFER";
        TransactionType type = TransactionType.customValueOf(description);

        then(type).isEqualTo(TransactionType.TRANSFER);
    }

    @Test
    void givenInvalidDescriptionThenThrowInvalidEnumDescriptionException() {
        thenThrownBy(() -> TransactionType.customValueOf("INVALID"))
            .hasMessage("INVALID");
    }
}
