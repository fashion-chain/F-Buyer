package com.hottop.core.model.commerce;

import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.ImageConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Data
public class CommerceService extends EntityBase {
    @Column(columnDefinition = "VARCHAR(31) NOT NULL UNIQUE COMMENT '服务名' ")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE COMMENT '服务描述' ")
    private String description;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '服务类型' ")
    private EServiceType type;

    @Column(columnDefinition = "JSON COMMENT '服务icon' ")
    @Convert(converter = ImageConverter.class)
    private Image icon;
}
