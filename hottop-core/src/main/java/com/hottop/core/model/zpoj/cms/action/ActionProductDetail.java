package com.hottop.core.model.zpoj.cms.action;

import com.hottop.core.model.zpoj.cms.enums.EActionType;

public class ActionProductDetail extends HottopActionBase {

    public ActionProductDetail() {
        super(EActionType.product_detail);
    }

    @Override
    public String getParams() {
        return null;
    }

    @Override
    public String convertToAString() {
        return null;
    }
}
