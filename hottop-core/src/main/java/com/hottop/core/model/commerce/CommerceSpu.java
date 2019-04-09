package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceServiceDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import com.hottop.core.model.zpoj.converter.*;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Data
public class CommerceSpu extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '品牌ID' ")
    private Long brandId;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '品类ID' ")
    private Long categoryId;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL DEFAULT '' COMMENT '商品标题' ")
    private String title;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '最小采购单元' ")
    private Integer purchaseUnit;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '库存' ")
    private Integer inventory;

    @Column(columnDefinition = "VARCHAR(63) DEFAULT '' COMMENT '售价' ")
    private String salePrice;

    @Column(columnDefinition = "VARCHAR(63) DEFAULT '' COMMENT '市场价' ")
    private String marketPrice;

    @Column(columnDefinition = "JSON NOT NULL COMMENT '轮播图' ")
    @Convert(converter = ListMediaConverter.class)
    private ArrayList<Media> carousel;

    @Column(columnDefinition = "JSON COMMENT '详情图' ")
    @Convert(converter = ListMediaConverter.class)
    private ArrayList<Media> info;

    @Column(columnDefinition = "JSON COMMENT '对照表' ")
    @Convert(converter = ListImageConverter.class)
    private ArrayList<Image> comparison;

    @Column(columnDefinition = "JSON COMMENT '商品服务' ")
    @Convert(converter = ListCommerceServiceDtoConverter.class)
    private ArrayList<CommerceServiceDto> services;

    @Column(columnDefinition = "JSON COMMENT '商品规格' ")
    @Convert(converter = ListCommerceSpecificationDtoConverter.class)
    private ArrayList<CommerceSpecificationDto> specifications;

    @Column(columnDefinition = "JSON COMMENT '商品属性' ")
    @Convert(converter = ListCommerceAttributeDtoConverter.class)
    private ArrayList<CommerceAttributeDto> attributes;
}
