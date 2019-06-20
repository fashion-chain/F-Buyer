package com.hottop.core.model.user;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import com.hottop.core.request.argument.filter.api.FieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldWidget;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceIndicator;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Created by lq on 2019/3/12.
 * 用户权限表
 */
@Data
@Entity
public class Capability extends EntityBase {

    @Column(columnDefinition = "varchar(50) NOT NULL UNIQUE COMMENT '权限名'")
    private String capabilityName;

    @Column(columnDefinition = "char(1) DEFAULT '0' COMMENT '状态，0正常 1禁用'")
    private String state;

    @Transient
    private String stateShowName;

    @Column(columnDefinition = "varchar(200) DEFAULT '' COMMENT '描述'")
    private String description;

    @Column(columnDefinition = "varchar(100) DEFAULT '' COMMENT 'url参数'")
    private String url;

    @Column(columnDefinition = "varchar(10) DEFAULT '' COMMENT 'method'")
    private String method;

    @Column(columnDefinition = "bigint DEFAULT NULL COMMENT '父id'")
    private Long pid;

    /**
     * 空构造
     */
    public Capability() {
    }

    /**
     * 构造器
     *
     * @param capabilityName
     * @param state
     * @param description
     */
    public Capability(String capabilityName, String state, String description, String url, String method) {
        this.capabilityName = capabilityName;
        this.state = state;
        this.description = description;
        this.url = url;
        this.method = method;
    }

    public Capability(Capability capability) {
        this.capabilityName = capability.capabilityName;
        this.state = capability.state;
        this.description = capability.description;
        this.url = capability.url;
        this.method = capability.method;
    }


    public static ProgramInterfaceIndicator getProgramInterfaceIndicator() throws Exception {
        return ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(Capability.class)
                //字段指示器
                .fieldIndicators(
                        FieldIndicator.FieldIndicatorBuilder.field(Capability_.CAPABILITY_NAME,
                                BaseConfiguration.getMessage("permission.name"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(Capability_.DESCRIPTION,
                                BaseConfiguration.getMessage("permission.description"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(Capability_.URL,
                                BaseConfiguration.getMessage("permission.url"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(Capability_.METHOD,
                                BaseConfiguration.getMessage("permission.method"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(Capability_.STATE,
                                BaseConfiguration.getMessage("permission.state"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).create()
                        ).create()
                )
                .requiredFields(Capability_.CAPABILITY_NAME, Capability_.DESCRIPTION, Capability_.STATE)
                //.sortableFields(Permission_.NAME)
                .create();
    }
}
