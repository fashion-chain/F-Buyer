package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

//product商品 action
@Getter
public class ActionProductDetail extends ActionBase {

    private Long productId;

    public ActionProductDetail(Long productId) {
        super(EActionType.productDetail);
        this.productId = productId;
    }
}
