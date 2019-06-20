package com.hottop.backstage.bguser.model;

import com.hottop.core.model.user.Role;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.service.EntityBaseService;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 后台用户实体类
 */
@Data
@Entity
public class BgUser extends EntityBase implements Serializable {

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "char(1) DEFAULT '1' COMMENT '用户状态，1正常 0禁用'")
    private String state;

    @Column(columnDefinition = "varchar(20) DEFAULT '' COMMENT '电话'")
    private String tel;

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT '邮箱'")
    @Email(message = "邮箱格式错误")
    private String email;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '备注'")
    private String remark;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '最后登录时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginLastTime;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '创建者id'")
    private Long creatorId;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '修改者id'")
    private Long modifyId;

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "ht_bguser_role", joinColumns = @JoinColumn(name = "ht_bguser_id"), inverseJoinColumns = @JoinColumn(name = "ht_role_id"))
    private List<Role> roles;
}
