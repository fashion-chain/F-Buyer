package com.hottop.core.model.zpoj.bean;

import com.hottop.core.model.cms.Activity;
import com.hottop.core.model.commerce.*;
import com.hottop.core.model.community.Community;
import com.hottop.core.model.community.Feed;
import com.hottop.core.model.user.User;

public enum EBusinessEntityType {
    product(CommerceSpu.class),
    retailProduct(CommerceRetailSpu.class),
    series(CommerceSeries.class),
    topic(Activity.class),
    community(Community.class),
    feed(Feed.class),
    purchaseOrder(CommercePurchaseOrder.class),
    retailOrder(CommerceRetailOrder.class),
    user(User.class);

    private Class<?> clazz;

    EBusinessEntityType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> entity() {
        return this.clazz;
    }
}
