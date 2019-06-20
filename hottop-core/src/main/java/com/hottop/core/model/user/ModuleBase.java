package com.hottop.core.model.user;

import com.hottop.core.model.user.enums.EModuleType;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

//抽象 模块base
@Data
public abstract class ModuleBase implements Serializable {

    //app用户id userId
    private Long userId;

    //成立时间
    private Date foundTime;

    //过期时间
    private Date expireTime;

    //返利比
    private Long rebate;

    //名称，名字
    private String name;

    //代理有效期总共多少天
    @Transient
    private Integer days;

    //模块类型
    private EModuleType type;
}
