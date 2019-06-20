package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.model.commerce.enums.EAttributeType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Data
public class CommerceAttribute extends EntityBase {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '属性名' ")
    @Size(max = 15)
    private String name;

    @Column(columnDefinition = "JSON COMMENT '推荐值' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> recommendationValues;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '属性类型' ")
    @Enumerated(EnumType.STRING)
    private EAttributeType type;
}
