package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class CommercePurchaseOrderSku extends EntityBase {

    @Column(columnDefinition = "BIGINT DEFAULT NULL COMMENT '采购订单id'")
    private Long purchaseOrderId;

    @Column(columnDefinition = "BIGINT DEFAULT NULL COMMENT '用户id'")
    private Long userId;

    @Column(columnDefinition = "BIGINT DEFAULT NULL COMMENT '商品spuId'")
    private Long spuId;

    @Column(columnDefinition = "BIGINT DEFAULT NULL COMMENT 'skuId'")
    private Long skuId;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'sku订单金额' ")
    private Long amount;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT 'sku支付金额' ")
    private Long payAmount;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '数量' ")
    private Integer quantity;

    private Long distributorId;

    private Long distributorRebate;

    private Long agentId;

    private Long agentRebate;
    
}
