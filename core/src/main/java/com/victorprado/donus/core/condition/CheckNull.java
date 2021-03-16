package com.victorprado.donus.core.condition;

import com.victorprado.donus.core.exception.condition.NotNullObjectException;

public final class CheckNull {

    private CheckNull() {

    }

    public static void check(Object object, String message) {
        if (object != null) {
            throw new NotNullObjectException(message);
        }
    }
}
