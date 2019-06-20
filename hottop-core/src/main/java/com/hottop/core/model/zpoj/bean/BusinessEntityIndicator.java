package com.hottop.core.model.zpoj.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessEntityIndicator implements Serializable {
    private EBusinessEntityType entityType;
    private String entityId;
}
