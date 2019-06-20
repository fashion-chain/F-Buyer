package com.hottop.core.model.user.enums;

public enum EUserAddressIsDefault {

    DEFAULT("0"), NOT_DEFAULT("1"), ;

    private String isDefault;

    EUserAddressIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
