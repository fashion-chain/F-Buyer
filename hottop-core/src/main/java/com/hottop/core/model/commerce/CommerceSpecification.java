package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Data
public class CommerceSpecification extends EntityBase {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '规格名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '推荐值' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> recommendationValues;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '规格类型' ")
    private ESpecificationType type;
}
