package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lq on 2019/3/12.
 * 用户角色类
 */
@Data
@Entity
public class Role extends EntityBase {

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '角色名'")
    private String roleName;

    @Column(columnDefinition = "char(1) DEFAULT '1' COMMENT '角色状态，1正常 0禁用'")
    private String state;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '备注'")
    private String remark;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '创建者id'")
    private String createrId;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '修改者id'")
    private String modifyId;

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "gb_role_permission", joinColumns = @JoinColumn(name = "gb_role_id"),
            inverseJoinColumns = @JoinColumn(name = "gb_permissions_id"))
    private List<Permission> permissions;

    /**
     * 空构造器
     */
    public Role() {
    }

    /**
     * 构造器
     *
     * @param roleName
     * @param state
     * @param remark
     */
    public Role(String roleName, String state, String remark) {
        this.roleName = roleName;
        this.state = state;
        this.remark = remark;
    }
}
