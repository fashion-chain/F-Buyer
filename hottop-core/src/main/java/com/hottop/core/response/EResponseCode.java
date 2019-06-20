package com.hottop.core.response;

public enum EResponseCode {
    OK(0),
    ERROR_INTERVAL(1),
    ERROR_INTERVAL_NOT_FOUND(2),

    ERROR_CMS_EXTRACT(100),
    ERROR_CMS_VALIDATION_FAILURE(101),
    ERROR_CMS_EXTRACTOR_NOT_FOUND(102),
    ERROR_CMS_COMPONENT_TYPE_NOT_SUPPORT(103),

    ERROR_TRADE_WX(10000),
    ERROR_TRADE_ALI(10001),

    TokenExpire(600),
    RefreshTokenExpire(601),//刷新token过期
    ;

    private Integer code;

    EResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
