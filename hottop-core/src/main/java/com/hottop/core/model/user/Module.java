package com.hottop.core.model.user;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lq on 2019/3/12.
 * 用户模块module
 */
@Data
@Entity
public class Module extends EntityBase {

    @Column(columnDefinition = "varchar(50) DEFAULT '' COMMENT '角色名'")
    private String moduleName;

    @Column(columnDefinition = "varchar(10) DEFAULT '0' COMMENT '角色状态，0正常 1禁用'")
    private String state;

    @Transient
    private String stateShowName;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '备注'")
    private String remark;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "gb_module_permission", joinColumns = @JoinColumn(name = "gb_module_id"),
            inverseJoinColumns = @JoinColumn(name = "gb_capability_id"))
    private List<Capability> capabilities;

    @Transient
    private List<Long> capabilityIds;

    /**
     * 空构造器
     */
    public Module() {
    }

    /**
     * 构造器
     *
     * @param moduleName
     * @param state
     * @param remark
     */
    public Module(String moduleName, String state, String remark) {
        this.moduleName = moduleName;
        this.state = state;
        this.remark = remark;
    }
}
