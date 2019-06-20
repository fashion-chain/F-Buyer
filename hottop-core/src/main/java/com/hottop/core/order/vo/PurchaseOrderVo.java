package com.hottop.core.order.vo;

import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.model.user.UserAddress;
import lombok.Data;

import java.util.List;

/**
 * 采购单vo
 */
@Data
public class PurchaseOrderVo {

    private UserAddress userAddress;

    private List<CommercePurchaseOrderSku> commercePurchaseOrderSkuList;
}
