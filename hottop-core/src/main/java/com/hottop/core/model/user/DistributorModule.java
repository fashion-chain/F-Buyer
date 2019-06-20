package com.hottop.core.model.user;

import com.hottop.core.model.user.enums.EModuleType;
import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//代理商模块
@Data
public class DistributorModule extends Application {

    //代理商id
    private Long agentId;

    //经销商店铺名
    private String storeName;

}
