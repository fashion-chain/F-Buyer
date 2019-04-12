package com.hottop.core.response;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import org.apache.commons.lang.StringUtils;

public enum EResponseResult {
    OK(EResponseCode.OK, ""),
    ERROR_INTERVAL(EResponseCode.ERROR_INTERVAL, "common.message.interval"),
    ERROR_REQUEST_ARGUMENT_PARSE(EResponseCode.ERROR_INTERVAL, "request.argument.json_parse"),
    ERROR_REQUEST_ARGUMENT_FILTER(EResponseCode.ERROR_INTERVAL, "request.argument.filter"),
    ERROR_SMS_SEND(EResponseCode.ERROR_INTERVAL, "sms.error");

    private Integer code;
    private String messageCode;

    EResponseResult(EResponseCode code, String messageCode) {
        this.code = code.getCode();
        this.messageCode = messageCode;//!StringUtils.isEmpty(messageCode) ? BaseConfiguration.getMessage(messageCode) : "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage(Object... objs) {
        return BaseConfiguration.getMessage(this.messageCode, objs);
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
