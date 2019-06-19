package com.hottop.core.model.cms.bean.action;

import java.lang.reflect.Type;

public enum EActionType {
    /* business */
    pageDetail,

    seriesList,
    seriesDetail,

    topicList,
    topicDetail,

    purchaseOrderList,
    purchaseOrderDetail,

    //零售订单
    retailOrderDetail,

    socialList,
    socialDetail,

    communityList,
    communityDetail,

    categoryList,
    categoryDetail,

    brandList,
    brandDetail,

    feedDetail,

    productDetail,
    retailProductDetail,

    userDetail,

    toolList,
    toolTeam,

    webView,

    /* interact */
    toast,
    dialog,
    upgrade,
    share;

    public static Class<? extends ActionBase> getCorrespondingType(EActionType actionType) {
        Class<? extends ActionBase> targetType = null;
        switch (actionType) {
            case brandDetail:
                targetType = ActionBrandDetail.class;
                break;
            case toast:
                targetType = ActionToast.class;
                break;
            case productDetail:
                targetType = ActionProductDetail.class;
                break;
            case purchaseOrderDetail:
                targetType = ActionPurchaseOrderDetail.class;
                break;
            case retailProductDetail:
                targetType = ActionRetailProductDetail.class;
                break;
            case seriesDetail:
                targetType = ActionSeries.class;
                break;

        }
        return targetType;
    }
}
