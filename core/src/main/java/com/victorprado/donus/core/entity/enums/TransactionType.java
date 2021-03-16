package com.victorprado.donus.core.entity.enums;

import com.victorprado.donus.core.exception.enums.InvalidEnumDescriptionException;

public enum TransactionType {

    TRANSFER("TRANSFER"),
    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT");

    private final String description;

    public String getDescription() {
        return description;
    }

    private TransactionType(String description) {
        this.description = description;
    }

    public static TransactionType customValueOf(String description) {
        for (TransactionType status : values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }

        throw new InvalidEnumDescriptionException(description);
    }
}
