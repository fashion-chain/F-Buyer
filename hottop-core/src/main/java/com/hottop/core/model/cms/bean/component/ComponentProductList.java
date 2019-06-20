package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.ELoadingDirection;
import com.hottop.core.request.argument.flag.FlagPageSizeRequest;

public class ComponentProductList extends ComponentEndless {

    public ComponentProductList() {
        super(EComponentType.productList);
        setUrl("/brand/filter");   //TODO 现在是以品牌做例子
        setDirection(ELoadingDirection.positive);
        setFlag(FlagPageSizeRequest.DEFAULT.getFlagResolver().toFlag());
    }
}
