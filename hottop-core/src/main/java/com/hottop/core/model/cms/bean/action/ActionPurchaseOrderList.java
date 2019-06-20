package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

@Getter
public class ActionPurchaseOrderList extends ActionBase {

    private Long purchaseOrderId;

    public ActionPurchaseOrderList(Long purchaseOrderId) {
        super(EActionType.purchaseOrderList);
        this.purchaseOrderId = purchaseOrderId;
    }
}
