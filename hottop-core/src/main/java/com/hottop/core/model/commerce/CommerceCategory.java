package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import com.hottop.core.model.zpoj.converter.ListCommerceAttributeDtoConverter;
import com.hottop.core.model.zpoj.converter.ListCommerceSpecificationDtoConverter;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.utils.tree.TreeNodeInterface;
import lombok.Data;
import sun.security.util.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CommerceCategory extends EntityBase implements TreeNodeInterface {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '品类名' ")
    @Size(max = 15)
    private String name;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '父品类ID' ")
    private Long parentId;

    @Column(columnDefinition = "JSON COMMENT '品类规格' ")
    @Convert(converter = ListCommerceSpecificationDtoConverter.class)
    private ArrayList<CommerceSpecificationDto> specifications;

    @Column(columnDefinition = "JSON COMMENT '品类属性' ")
    @Convert(converter = ListCommerceAttributeDtoConverter.class)
    private ArrayList<CommerceAttributeDto> attributes;

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public Long getValue() {
        return super.getId();
    }

}
