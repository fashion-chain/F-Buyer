package com.hottop.core.security.properties;

public class ApiProperties {

    private String loginType = "JSON";

    private String jwtExpireTime = "3600";//默认一个小时过期

    private String jwtRefreshTime = 3600*24*7+"";//默认刷新时间 1周

    private String loginFormUrl = "/authentication/api/form";

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getJwtExpireTime() {
        return jwtExpireTime;
    }

    public void setJwtExpireTime(String jwtExpireTime) {
        this.jwtExpireTime = jwtExpireTime;
    }

    public String getLoginFormUrl() {
        return loginFormUrl;
    }

    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }

    public String getJwtRefreshTime() {
        return jwtRefreshTime;
    }

    public void setJwtRefreshTime(String jwtRefreshTime) {
        this.jwtRefreshTime = jwtRefreshTime;
    }
}
