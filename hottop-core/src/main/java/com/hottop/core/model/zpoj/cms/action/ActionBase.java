package com.hottop.core.model.zpoj.cms.action;

import com.hottop.core.model.zpoj.cms.IAction;
import com.hottop.core.model.zpoj.cms.enums.EActionType;

import java.io.Serializable;

public abstract class ActionBase implements IAction, Serializable {
    private EActionType actionType;

    public ActionBase(EActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public EActionType getPath() {
        return actionType;
    }
}
