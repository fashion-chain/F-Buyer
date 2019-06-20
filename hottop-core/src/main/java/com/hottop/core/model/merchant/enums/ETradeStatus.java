package com.hottop.core.model.merchant.enums;

public enum ETradeStatus {
    unpaid((byte) 0000);

    private byte statusPath;

    ETradeStatus(byte statusPath) {
        this.statusPath = statusPath;
    }
}
