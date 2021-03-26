package com.victorprado.donus.entrypoint.response;

import lombok.Getter;

@Getter
public class EntryPointResponse<T> {

    private T data;
    private String message;
    private Integer code;

    public EntryPointResponse(T data, String message, Integer code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public EntryPointResponse(T data, Integer code) {
        this.data = data;
        this.code = code;
    }
}
