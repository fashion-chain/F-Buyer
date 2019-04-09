package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.commerce.enums.EAttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CommerceAttributeDto implements Serializable {
    private String name;

    private EAttributeType type;

    private ArrayList<String> recommendationValues;
}
