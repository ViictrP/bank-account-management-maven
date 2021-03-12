package com.victorprado.donus.core.condition;

import com.victorprado.donus.core.exception.NotNullObjectException;
import java.util.Optional;

public final class CheckNull {

    private CheckNull() {

    }

    public static void check(Object object, String message) {
        if (object != null) {
            throw new NotNullObjectException(message);
        }
    }

    public static void check(Optional object, String message) {
        if (object.isPresent()) {
            throw new NotNullObjectException(message);
        }
    }
}
