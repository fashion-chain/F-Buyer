package com.hottop.core.model.zpoj.commerce.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommerceBrandDto implements Serializable {
    private String name;

    private String country;

    private Long categoryId;

    private String description;

    private String avatar;
}
