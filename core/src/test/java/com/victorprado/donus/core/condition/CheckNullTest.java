package com.victorprado.donus.core.condition;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.victorprado.donus.core.exception.NotNullObjectException;
import org.junit.jupiter.api.Test;

class CheckNullTest {

    @Test
    void givenNotNullParameterThenThrowNotNullObjectException() {
        thenThrownBy(() -> CheckNull.check(new Object(), "Null object"))
            .isInstanceOf(NotNullObjectException.class)
            .hasMessage("Null object");
    }

    @Test
    void givenNullParameterThenDoNothing() {
        assertThatCode(() -> CheckNull.check(null, "not null"))
            .doesNotThrowAnyException();
    }
}
