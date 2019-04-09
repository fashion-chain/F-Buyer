package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class CommercePurchaseOrderSku extends EntityBase {
    private Long purchaseOrderId;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private Long amount;

    private Long payAmount;

    private Integer quantity;

    
}
