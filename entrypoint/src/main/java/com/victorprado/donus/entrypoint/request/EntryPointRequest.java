package com.victorprado.donus.entrypoint.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntryPointRequest<T> {

    private T data;
}
