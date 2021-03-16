package com.victorprado.donus.core.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Upgradeable extends Creatable {

    protected LocalDateTime lastModifiedDate;
}
