package com.hottop.core.model.zpoj.commerce.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommerceBrandDto implements Serializable {
    private String name;

    private String country;

<<<<<<< HEAD
    private String description;
=======
    private Long categoryId;

    private String description;

    private String avatar;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
