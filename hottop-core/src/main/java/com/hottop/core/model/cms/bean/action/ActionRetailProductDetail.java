package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

//retailProduct 零售商品 action
@Getter
public class ActionRetailProductDetail extends ActionBase{

    private Long retailProductId;

    public ActionRetailProductDetail(Long retailProductId) {
        super(EActionType.retailProductDetail);
        this.retailProductId = retailProductId;
    }
}
