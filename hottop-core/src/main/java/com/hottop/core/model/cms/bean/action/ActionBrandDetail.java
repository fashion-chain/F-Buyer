package com.hottop.core.model.cms.bean.action;

public class ActionBrandDetail extends ActionBase {

    private Long brandId;

    public ActionBrandDetail(Long brandId) {
        super(EActionType.brandDetail);
        this.brandId = brandId;
    }
}
