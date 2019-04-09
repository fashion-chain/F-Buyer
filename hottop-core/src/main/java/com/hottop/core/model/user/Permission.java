package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by lq on 2019/3/12.
 * 用户权限表
 */
@Data
@Entity
public class Permission extends EntityBase {

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '权限名'")
    private String permissionName;

    @Column(columnDefinition = "char(1) DEFAULT '0' COMMENT '用户状态，1正常 0禁用'")
    private String state;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '描述'")
    private String description;

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT 'url'")
    private String url;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '父id'")
    private String pid;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '创建者id'")
    private String createrId;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '修改者id'")
    private String modifyId;

    /**
     * 空构造
     */
    public Permission() {
    }

    /**
     * 构造器
     *
     * @param permissionName
     * @param state
     * @param description
     * @param url
     */
    public Permission(String permissionName, String state, String description, String url) {
        this.permissionName = permissionName;
        this.state = state;
        this.description = description;
        this.url = url;
    }
}
