package com.hottop.core.model.zpoj.cms.action;

import com.hottop.core.model.zpoj.cms.enums.EActionType;

public abstract class HottopActionBase extends ActionBase {

    public HottopActionBase(EActionType actionType) {
        super(actionType);
    }

    @Override
    public String getSchema() {
        return "hottop";
    }

    @Override
    public String getHost() {
        return "com.hottop";
    }
}
