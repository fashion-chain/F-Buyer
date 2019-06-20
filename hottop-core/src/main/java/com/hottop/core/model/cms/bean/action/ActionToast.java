package com.hottop.core.model.cms.bean.action;

public class ActionToast extends ActionBase {

    private String message;

    public ActionToast(String message) {
        super(EActionType.toast);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
