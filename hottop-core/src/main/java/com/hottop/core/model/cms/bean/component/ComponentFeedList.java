package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.ELoadingDirection;
import com.hottop.core.request.argument.flag.FlagPageSizeRequest;

public class ComponentFeedList extends ComponentEndless {

    public ComponentFeedList() {
        super(EComponentType.productList);
        setUrl("/brand/filter?communityId=1");   //TODO 现在是以品牌做例子
        setDirection(ELoadingDirection.positive);
        setFlag(FlagPageSizeRequest.DEFAULT.getFlagResolver().toFlag());
    }
}
