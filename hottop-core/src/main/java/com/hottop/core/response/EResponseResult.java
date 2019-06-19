package com.hottop.core.response;

import com.hottop.core.config.BaseConfiguration;

public enum EResponseResult {
    OK(EResponseCode.OK, ""),
    ERROR_INTERVAL(EResponseCode.ERROR_INTERVAL, "common.message.interval"),
    ERROR_INTERVAL_NOT_FOUND(EResponseCode.ERROR_INTERVAL_NOT_FOUND, "common.message.interval.notFound"),

    ERROR_CMS_EXTRACT(EResponseCode.ERROR_CMS_EXTRACT, "common.error.cms"),
    ERROR_CMS_VALIDATION_FAILURE(EResponseCode.ERROR_CMS_VALIDATION_FAILURE, "common.error.cms.validationFailure"),
    ERROR_CMS_EXTRACTOR_NOT_FOUND(EResponseCode.ERROR_CMS_EXTRACTOR_NOT_FOUND, "common.error.cms.notFound"),
    ERROR_CMS_COMPONENT_TYPE_NOT_SUPPORT(EResponseCode.ERROR_CMS_COMPONENT_TYPE_NOT_SUPPORT, "common.error.cms.notSupport"),

    ERROR_REQUEST_ARGUMENT_PARSE(EResponseCode.ERROR_INTERVAL, "request.argument.json_parse"),
    ERROR_REQUEST_ARGUMENT_FILTER(EResponseCode.ERROR_INTERVAL, "request.argument.filter"),
    ERROR_SMS_SEND(EResponseCode.ERROR_INTERVAL, "sms.error"),

    ERROR_TRADE_WX_PAYMENT_ERROR(EResponseCode.ERROR_TRADE_WX, "trade.wx.error"),
    ERROR_TRADE_ALI_PAYMENT_ERROR(EResponseCode.ERROR_TRADE_ALI, "trade.ali.error"),

    //---------- auth ------------
    ERROR_AUTHENTICATION_FAIL(EResponseCode.ERROR_INTERVAL, "auth.fail"),
    AUTHENTICATION_SUCCESS_TOKEN(EResponseCode.OK, "authentication.success.token"),
    AUTHENTICATION_ERROR_NeedToken(EResponseCode.ERROR_INTERVAL, "authentication.error.needToken"),
    AUTHENTICATION_ERROR_tokenExpire(EResponseCode.ERROR_INTERVAL, "authentication.error.tokenExpire"),
    AUTHENTICATION_ERROR_tokenIllegal(EResponseCode.ERROR_INTERVAL, "authentication.error.tokenIllegal"),
    AUTHENTICATION_ERROR_tokenSignIn(EResponseCode.ERROR_INTERVAL, "authentication.error.tokenSignIn"),

    //----------- sms -------------
    SMS_SUCCESS_SEND(EResponseCode.OK, "sms.send.success"),
    SMS_ERROR_SEND(EResponseCode.ERROR_INTERVAL, "sms.send.error"),
    SMS_ERROR_VERIFY(EResponseCode.ERROR_INTERVAL, "sms.verify.error"),
    SMS_ERROR_TimeTooShort(EResponseCode.ERROR_INTERVAL, "sms.send.error.timeTooShort"),

    //----------- user ------------
    USER_SUCCESS_REGISTER(EResponseCode.OK, "user.register.success"),
    USER_ERROR_REGISTER_FAIL(EResponseCode.ERROR_INTERVAL, "user.register.error"),
    USER_ERROR_REGISTER_HAS_REGISTER(EResponseCode.ERROR_INTERVAL, "user.register.has.register"),
    USER_ERROR_PHONE_NotExist(EResponseCode.ERROR_INTERVAL, "user.phone.notExist"),
    USER_ERROR_PHONE(EResponseCode.ERROR_INTERVAL, "user.phone.error"),
    USER_ERROR_PASSWORD(EResponseCode.ERROR_INTERVAL, "user.password.error"),
    USER_ERROR_PASSWORDTwice(EResponseCode.ERROR_INTERVAL, "user.passwordTwice.error"),
    USER_SUCCESS_PASSWORD_CHANGE(EResponseCode.OK, "user.success.password.change"),
    USER_ERROR_PASSWORD_CHANGE(EResponseCode.OK, "user.error.password.change"),
    USER_ERROR_LOGIN_NAME_PASSWORD(EResponseCode.ERROR_INTERVAL, "user.login.name-password.error"),

    //------------ userAddress -------------
    UserAddress_ERROR_SAVE(EResponseCode.ERROR_INTERVAL, "userAddress.error.save"),
    UserAddress_SUCCESS_SAVE(EResponseCode.OK, "userAddress.success.save"),
    UserAddress_ERROR_UPDATE(EResponseCode.ERROR_INTERVAL, "userAddress.error.update"),
    UserAddress_SUCCESS_UPDATE(EResponseCode.OK, "userAddress.success.update"),
    UserAddress_ERROR_ZipCode(EResponseCode.OK, "userAddress.error.zipCode"),

    //------------- jwt --------------------
    JWT_TOKEN_EXPIRE(EResponseCode.TokenExpire, "jwt.token.expire"),
    JWT_RefreshToken_EXPIRE(EResponseCode.RefreshTokenExpire, "jwt.refreshToken.expire"),

    //------------ upload file --------------
    UploadFile_ERROR_PATH(EResponseCode.ERROR_INTERVAL, "uploadFile.error.path"),
    UploadFile_ERROR_FileNotNull(EResponseCode.ERROR_INTERVAL, "uploadFile.error.fileNotNull"),
    UploadFile_ERROR_ImageMax(EResponseCode.ERROR_INTERVAL, "common.file.error.imageMax"),
    UploadFile_ERROR_VideoMax(EResponseCode.ERROR_INTERVAL, "common.file.error.videoMax"),

    //------------ common -------------
    COMMON_SUCCESS_GetDetail(EResponseCode.OK, "common.success.getDetail"),
    COMMON_ERROR_GetDetail(EResponseCode.ERROR_INTERVAL, "common.error.getDetail"),
    COMMON_SUCCESS_ADD(EResponseCode.OK, "common.success.add"),
    COMMON_ERROR_ADD(EResponseCode.ERROR_INTERVAL, "common.error.add"),
    COMMON_SUCCESS_DELETE(EResponseCode.OK, "common.success.delete"),
    COMMON_ERROR_DELETE(EResponseCode.ERROR_INTERVAL, "common.error.delete"),
    COMMON_SUCCESS_UPDATE(EResponseCode.OK, "common.success.update"),
    COMMON_ERROR_UPDATE(EResponseCode.ERROR_INTERVAL, "common.error.update"),
    //--- common file ---
    COMMON_ERROR_FileUploadName(EResponseCode.ERROR_INTERVAL, "common.file.error.fileUploadName"),


    ;

    private Integer code;
    private String messageCode;

    EResponseResult(EResponseCode code, String messageCode) {
        this.code = code.getCode();
        this.messageCode = messageCode;//!StringUtils.isEmpty(messageCode) ? BaseConfiguration.getMessage(messageCode) : "";
    }

    public Integer getCode() {
        return code;
    }

    public String getMessageCode() {
        return messageCode;
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
