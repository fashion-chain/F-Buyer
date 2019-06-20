package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class CommerceRetailOrderSku extends EntityBase {
    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT '零售订单id'")
    private Long retailOrderId;

    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT '用户id'")
    private Long userId;

    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT '商品spuId'")
    private Long retailSpuId;

    @Column(columnDefinition = "INT(11) DEFAULT NULL COMMENT 'skuId'")
    private Long retailSkuId;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'sku订单金额' ")
    private Long amount;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'sku支付金额' ")
    private Long payAmount;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '数量' ")
    private Integer quantity;
}
