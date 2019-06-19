package com.hottop.core.model.user;

import com.hottop.core.model.user.enums.EModuleType;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

//代理商模块
@Data
public class AgentModule extends ModuleBase {


    //区域id
    private Long regionId;

    private String region;

    private Long level;

    private Map<Long,String> levelMap;

    public AgentModule() {
        super.setType(EModuleType.agent);
    }
}
