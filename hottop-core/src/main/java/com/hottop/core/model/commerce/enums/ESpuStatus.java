package com.hottop.core.model.commerce.enums;

public enum ESpuStatus {
    init("commerce.spu.status.init"),
    up("commerce.spu.status.up"),
    down("commerce.spu.status.down"),
    ;

    static {
        init.registerOperations(ESpuStatus.up);
        up.registerOperations(ESpuStatus.down);
        down.registerOperations(ESpuStatus.up);
    }

    String msgCode;
    ESpuStatus[] eSpuStatuses;

    ESpuStatus(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public ESpuStatus[] geteSpuStatuses() {
        return eSpuStatuses;
    }

    public void seteSpuStatuses(ESpuStatus[] eSpuStatuses) {
        this.eSpuStatuses = eSpuStatuses;
    }

    void registerOperations(ESpuStatus... supportedOperations){
        eSpuStatuses = supportedOperations;
    }

}
