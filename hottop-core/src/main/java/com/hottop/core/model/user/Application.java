package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.ListModuleConverter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台应用appellation appId
 * 后台用户
 */
@Data
@Entity
public class Application extends EntityBase implements Serializable {

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "char(1) DEFAULT '1' COMMENT '用户状态，0正常 1禁用'")
    private String state;

    @Transient
    private String stateShowName;

    @Transient
    private Map<String, String> stateMap;

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

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "gb_application_module", joinColumns = @JoinColumn(name = "gb_application_id"),
            inverseJoinColumns = @JoinColumn(name = "gb_module_id"))
    private List<Module> modules;

    @Transient
    private List<Long> rolesIds;

    @Column(columnDefinition = "JSON COMMENT '模块列表'")
    @Convert(converter = ListModuleConverter.class)
    private ArrayList<ModuleBase> modulesList;

}
