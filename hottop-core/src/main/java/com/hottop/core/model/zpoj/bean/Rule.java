package com.hottop.core.model.zpoj.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Rule implements Serializable {
    private ERuleType ruleType;

    enum ERuleType {
        simple
    }
}
