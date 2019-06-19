package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.zpoj.bean.BusinessProvider;
import com.hottop.core.model.zpoj.bean.BusinessProviderInfo;
import com.hottop.core.model.zpoj.bean.Image;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ComponentPurchaseOrder extends ComponentBase {

    private BusinessProviderInfo providerInfo;

    private String tradeNo;//订单号

    private String status;//状态

    private List<CommercePurchaseOrderSku> skus;

    private String totalSkuCount;

    private String totalCount;

    private String totalAmount;

    public ComponentPurchaseOrder() {
        super(EComponentType.purchaseOrder);
    }
}
