package com.hottop.core.model.user;

import com.hottop.core.model.user.enums.EUserIdentityType;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户授权信息表
 */
@Entity
@Data
public class UserAuthorization extends EntityBase {

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '用户表id'")
    private Long userId;

    @Column(columnDefinition = "varchar(10) DEFAULT '' COMMENT '用户标识类型，如邮箱、微信'")
    private EUserIdentityType eUserIdentityType;

    @Column(columnDefinition = "varchar(40) DEFAULT '' COMMENT '用户标识值，比如具体邮箱、微信appid'")
    private String identifier;

    @Column(columnDefinition = "varchar(80) DEFAULT NULL COMMENT '站外的不保存'")
    private String password;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'token'")
    private String token;

    @Column(columnDefinition = "varchar(50) DEFAULT NULL COMMENT 'refresh token'")
    private String refreshToken;

}
