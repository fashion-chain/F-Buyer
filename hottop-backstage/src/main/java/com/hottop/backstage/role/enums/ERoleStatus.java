package com.hottop.backstage.role.enums;

public enum ERoleStatus {

    ok("0"), disable("1");

    public String status;

    ERoleStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
