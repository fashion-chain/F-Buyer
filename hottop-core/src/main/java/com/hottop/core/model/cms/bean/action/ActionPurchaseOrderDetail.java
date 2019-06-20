package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

@Getter
public class ActionPurchaseOrderDetail extends ActionBase {

    private Long purchaseOrderId;

    public ActionPurchaseOrderDetail(Long purchaseOrderId) {
        super(EActionType.purchaseOrderDetail);
        this.purchaseOrderId = purchaseOrderId;
    }
}
