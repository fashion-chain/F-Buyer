package com.hottop.core.model.community.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class HashTag implements Serializable {
    private Integer indices;

    private String topicName;
    private String topicId;
}
