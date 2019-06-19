package com.hottop.core.model.community.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Link implements Serializable {
    private Integer indices;

    private String name;
    private String url;
}
