package com.hottop.core.response;

public enum EResponseCode {
    OK(0), ERROR_INTERVAL(1);

    private Integer code;

    EResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
