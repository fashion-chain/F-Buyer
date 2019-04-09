package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSkuSpecificationIndicator;
import com.hottop.core.model.zpoj.converter.CommerceSkuSpecificationIndicatorConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
@Data
public class CommerceSku extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'spuId' ")
    private Long spuId;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '售价（单位：分）' ")
    private Long salePrice;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '市场价（单位：分）' ")
    private Long marketPrice;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '库存' ")
    private Integer inventory;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '最小采购单元' ")
    private Integer purchaseUnit;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL DEFAULT '' COMMENT '副标题' ")
    private String subtitle;

    @Column(columnDefinition = "JSON COMMENT '商品SKU信息' ")
    @Convert(converter = CommerceSkuSpecificationIndicatorConverter.class)
    private CommerceSkuSpecificationIndicator indicators;
}
