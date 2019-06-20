package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

<<<<<<< HEAD
=======
import javax.persistence.Column;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import javax.persistence.Entity;

@Entity
@Data
public class CommercePurchaseOrderSku extends EntityBase {
<<<<<<< HEAD
    private Long purchaseOrderId;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private Long amount;

    private Long payAmount;

    private Integer quantity;

=======

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
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    
}
