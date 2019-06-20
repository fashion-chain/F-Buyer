package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
<<<<<<< HEAD

import javax.persistence.Column;
=======
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

/**
 * 用户地址实体
 */
<<<<<<< HEAD
=======
@Data
@Entity
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
public class UserAddress extends EntityBase {

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '用户实体id'")
    private Long userId;

<<<<<<< HEAD
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
=======
    @Column(columnDefinition = "varchar(10) COMMENT '邮编'")
    private String zipCode;

    @Column(columnDefinition = "varchar(10) COMMENT '省份编码'")
    private String province;

    @Column(columnDefinition = "varchar(10)COMMENT '城市编码'")
    private String city;

    @Column(columnDefinition = "varchar(10) COMMENT '地区、城区编码'")
    private String area;

    @Column(columnDefinition = "varchar(100)COMMENT '具体地址'")
    @Size(max = 100, message = "最大长度是100")
    private String address;

    @Column(columnDefinition = "varchar(2) COMMENT '是否是默认地址 1 是默认地址'")
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    private String isDefault;

    public UserAddress() {
    }

<<<<<<< HEAD
    public UserAddress(Long userId, String zipCode, String province, String city, String district, String address, String isDefault) {
=======
    public UserAddress(Long userId, String zipCode, String province, String city, String area, String address, String isDefault) {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        this.userId = userId;
        this.zipCode = zipCode;
        this.province = province;
        this.city = city;
<<<<<<< HEAD
        this.district = district;
=======
        this.area = area;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        this.address = address;
        this.isDefault = isDefault;
    }
}
