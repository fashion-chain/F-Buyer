package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 用户地址实体
 */
@Data
@Entity
public class UserAddress extends EntityBase {

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '用户实体id'")
    private Long userId;

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
    private String isDefault;

    public UserAddress() {
    }

    public UserAddress(Long userId, String zipCode, String province, String city, String area, String address, String isDefault) {
        this.userId = userId;
        this.zipCode = zipCode;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.isDefault = isDefault;
    }
}
