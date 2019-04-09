package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 省份 城市实体
 */
@Data
@Entity
public class ProvinceCity extends EntityBase {

    @Column(columnDefinition = "varchar(10) NOT NULL UNIQUE COMMENT '省市区编号'")
    private String number;

    @Column(columnDefinition = "varchar(10) COMMENT '父 省市区编号'")
    private String pNumber;

    @Column(columnDefinition = "varchar(2) COMMENT '级别' ")
    private String level;

    @Column(columnDefinition = "varchar(20) COMMENT '省市区名称'")
    private String name;

    @Column(columnDefinition = "varchar(40) COMMENT '省市区全称'")
    private String mergerName;

    @Column(columnDefinition = "varchar(20) COMMENT '邮编' ")
    private String zipCode;
}
