package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.model.commerce.enums.EAttributeType;
import lombok.Data;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
=======
import javax.persistence.*;
import javax.validation.constraints.Size;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import java.util.ArrayList;

@Entity
@Data
public class CommerceAttribute extends EntityBase {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '属性名' ")
<<<<<<< HEAD
=======
    @Size(max = 15)
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    private String name;

    @Column(columnDefinition = "JSON COMMENT '推荐值' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> recommendationValues;

<<<<<<< HEAD
    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '属性类型' ")
=======
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '属性类型' ")
    @Enumerated(EnumType.STRING)
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    private EAttributeType type;
}
