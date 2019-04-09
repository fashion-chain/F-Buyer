package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;

import javax.persistence.Column;

/**
 * 用户地址实体
 */
public class UserAddress extends EntityBase {

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '用户实体id'")
    private Long userId;

    @Column(columnDefinition = "varchar(10）COMMENT '邮编'")
    private String zipCode;

    @Column(columnDefinition = "varchar(10）COMMENT '省份编码'")
    private String province;

    @Column(columnDefinition = "varchar(10）COMMENT '城市编码'")
    private String city;

    @Column(columnDefinition = "varchar(10）COMMENT '地区、城区编码'")
    private String district;

    @Column(columnDefinition = "varchar(100）COMMENT '具体地址'")
    private String address;

    @Column(columnDefinition = "varchar(2）COMMENT '是否是默认地址'")
    private String isDefault;

    public UserAddress() {
    }

    public UserAddress(Long userId, String zipCode, String province, String city, String district, String address, String isDefault) {
        this.userId = userId;
        this.zipCode = zipCode;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.isDefault = isDefault;
    }
}
