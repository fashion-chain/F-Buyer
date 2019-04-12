package com.hottop.core.security.validate.code.sms;

public enum ESmsCodeType {

    Login("smsLogin"), Registration("smsRegister");
    private String smsCodeType;

    ESmsCodeType(String smsCodeType) {
        this.smsCodeType = smsCodeType;
    }
}
