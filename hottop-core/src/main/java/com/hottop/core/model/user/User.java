package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.*;
<<<<<<< HEAD
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@NamedEntityGraph(name = "User.roles",attributeNodes = {@NamedAttributeNode("roles")})
@Data
@Entity
public class User extends EntityBase {
=======
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NamedEntityGraph(name = "User.modules",attributeNodes = {@NamedAttributeNode("modules")})
@Data
@Entity
public class User extends EntityBase implements Serializable {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "char(1) DEFAULT '1' COMMENT '用户状态，1正常 0禁用'")
    private String state;

    @Column(columnDefinition = "varchar(20) DEFAULT '' COMMENT '电话'")
    private String tel;

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT '邮箱'")
<<<<<<< HEAD
=======
    @Email(message = "邮箱格式错误")
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    private String email;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '备注'")
    private String remark;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '最后登录时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginLastTime;

<<<<<<< HEAD
    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '创建者id'")
    private Long creatorId;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '修改者id'")
    private Long modifyId;

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "gb_user_role", joinColumns = @JoinColumn(name = "gb_user_id"), inverseJoinColumns = @JoinColumn(name = "gb_role_id"))
    private List<Role> roles;
=======
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "gb_user_module", joinColumns = @JoinColumn(name = "gb_user_id"),
            inverseJoinColumns = @JoinColumn(name = "gb_module_id"))
    private List<Module> modules;

    //用户对应的application id
    @Column(columnDefinition = "INT(11) COMMENT '后台应用appId'")
    private Long appId;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

    /**
     * 空构造器
     */
    public User() {
    }

    /**
     * 构造器
     *
     * @param password
     * @param username
     * @param state
     * @param remark
     */
    public User(String password, String username, String state, String remark) {
        this.password = password;
        this.username = username;
        this.state = state;
        this.remark = remark;
    }
}
