package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.ImageConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Data
public class CommerceBrand extends EntityBase {

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '品牌名' ")
    private String name;

    @Column(columnDefinition = "VARCHAR(63) NOT NULL COMMENT '品牌所属国家' ")
    private String country;

    @Column(columnDefinition = "VARCHAR(1023) COMMENT '品牌描述' ")
    private String description;

    @Column(columnDefinition = "JSON COMMENT '品牌头像' ")
    @Convert(converter = ImageConverter.class)
    private Image avatar;
}
