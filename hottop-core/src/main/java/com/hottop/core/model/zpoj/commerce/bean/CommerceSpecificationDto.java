package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.zpoj.bean.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CommerceSpecificationDto implements Serializable {
    private String name;

    private ESpecificationType type;

    private ArrayList<String> recommendationValues;

}
