package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class ActionBase implements Serializable {
    private EActionType actionType;

    public ActionBase(EActionType actionType) {
        this.actionType = actionType;
    }
}
