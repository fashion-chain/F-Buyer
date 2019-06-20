package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.zpoj.converter.CommerceSkuSpecificationIndicatorConverter;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.io.Serializable;

@Data
public class CommerceSkuDto implements Serializable {

    private Long spuId;

    private Long salePrice;

    private Long marketPrice;

    private Integer inventory;

    private Integer purchaseUnit;

    private String subtitle;

    private CommerceSkuSpecificationIndicator indicators;
}
