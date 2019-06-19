package com.hottop.core.model.user.enums;

public enum  EPermissionStatus {
    ok("0"),disable("1") ;

    String status;

    private EPermissionStatus(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
