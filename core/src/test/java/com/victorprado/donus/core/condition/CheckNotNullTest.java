package com.victorprado.donus.core.condition;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.victorprado.donus.core.exception.NullObjectException;
import org.junit.jupiter.api.Test;

class CheckNotNullTest {

    @Test
    void givenNullParameterThenThrowNullObjectException() {
        thenThrownBy(() -> CheckNotNull.check(null, "Null object"))
            .isInstanceOf(NullObjectException.class)
            .hasMessage("Null object");
    }

    @Test
    void givenNotNullParameterThenDoNothing() {
        assertThatCode(() -> CheckNotNull.check(new Object(), "not null"))
            .doesNotThrowAnyException();
    }
}
