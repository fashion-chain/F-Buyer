package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户信息实体
 */
@Data
@Entity
public class UserInfo extends EntityBase {

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '用户实体id'")
    private Long userId;

    @Column(columnDefinition = "varchar(20) COMMENT '用户真实姓名'")
    private String realName;

    @Column(columnDefinition = "varchar(2) COMMENT '用户证件类型'")
    private String identityCardType;

    @Column(columnDefinition = "varchar(30) COMMENT '用户证件号码'")
    private String identityCardNo;

    @Column(columnDefinition = "varchar(1) COMMENT '用户性别'")
    private String gender;

<<<<<<< HEAD
    public UserInfo() {
    }

    public UserInfo(Long userId, String realName, String identityCardType, String identityCardNo, String gender) {
=======
    @Column(columnDefinition = "INT(4) DEFAULT 0 COMMENT '用户等级' ")
    private Integer level;

    public UserInfo() {
    }

    public UserInfo(Long userId, String realName, String identityCardType, String identityCardNo, String gender,
                    Integer level) {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        this.userId = userId;
        this.realName = realName;
        this.identityCardType = identityCardType;
        this.identityCardNo = identityCardNo;
        this.gender = gender;
<<<<<<< HEAD
=======
        this.level = level;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }
}
