package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import com.hottop.core.model.zpoj.converter.ListCommerceAttributeDtoConverter;
import com.hottop.core.model.zpoj.converter.ListCommerceSpecificationDtoConverter;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Data
public class CommerceCategory extends EntityBase {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '品类名' ")
    private String name;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '父品类ID' ")
    private Long parentId;

    @Column(columnDefinition = "JSON COMMENT '品类规格' ")
    @Convert(converter = ListCommerceSpecificationDtoConverter.class)
    private ArrayList<CommerceSpecificationDto> specifications;

    @Column(columnDefinition = "JSON COMMENT '品类属性' ")
    @Convert(converter = ListCommerceAttributeDtoConverter.class)
    private ArrayList<CommerceAttributeDto> attributes;
}
