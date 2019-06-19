package com.hottop.core.order.vo;

import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderDetailVo {

    private CommercePurchaseOrder commercePurchaseOrder;

    private List<CommercePurchaseOrderSku> commercePurchaseOrderSkuList;
}
