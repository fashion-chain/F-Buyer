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

<<<<<<< HEAD
    private EAttributeType type;

=======
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    private ArrayList<String> recommendationValues;
}
