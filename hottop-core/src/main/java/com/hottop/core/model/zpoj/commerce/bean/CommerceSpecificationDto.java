package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.commerce.enums.ESpecificationType;
<<<<<<< HEAD
=======
import com.hottop.core.model.zpoj.bean.Image;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
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
<<<<<<< HEAD
=======

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
