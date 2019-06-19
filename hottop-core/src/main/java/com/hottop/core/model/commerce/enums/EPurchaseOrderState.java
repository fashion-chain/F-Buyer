package com.hottop.core.model.commerce.enums;

import com.hottop.core.feature.status.EStatusEvent;

public enum EPurchaseOrderState {

    prePay,//待支付
    preDeliver,//待发货
    preReceive,//待收货
    orderFinish,//订单完成
    orderClose;//订单关闭

}
