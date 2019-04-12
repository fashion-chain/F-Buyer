package com.hottop.core.model.user.enums;

public enum EUserStatus {
    ok("1"),disable("0") ;

    private String status;

    private EUserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
