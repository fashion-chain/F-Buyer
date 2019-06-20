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
public class CommerceRetailSku extends EntityBase {

    //商品sku key 标识
    @Column(columnDefinition = "JSON COMMENT '商品SKU信息' ")
    @Convert(converter = CommerceSkuSpecificationIndicatorConverter.class)
    private CommerceSkuSpecificationIndicator indicators;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '售价（单位：分）' ")
    private Long salePrice;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '库存' ")
    private Long inventory;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL DEFAULT '' COMMENT '零售备注' ")
    private String remark;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'retailSku 关联 retailSpuId'")
    private Long retailSpuId;

}
