package com.hottop.core.model.cms.bean.component;

import java.lang.reflect.Type;

public enum EComponentType {
    /* cell */
    image,
    text,
    tab,
    feed,
    product,
    retailProduct,
    series,
    cabinetCell,
    navigator,
    purchaseOrder,
    community,
    user,
    exhibition,

    /* complex */
    switcher(EComponentType.tab),
    banner(EComponentType.image),
    cabinet(EComponentType.cabinetCell),
    exhibitionBox(EComponentType.image),

    productList,
    feedList,
    seriesList;

    private EComponentType[] availableChildComponentTypes;

    EComponentType() {}

    EComponentType(EComponentType... availableChildComponentTypes) {
        this.availableChildComponentTypes = availableChildComponentTypes;
    }

    public EComponentType[] getAvailableChildComponentTypes() {
        return availableChildComponentTypes;
    }

    public boolean isValidChildComponentType(EComponentType componentType) {
        if (availableChildComponentTypes == null) {
            return false;
        }
        for (EComponentType availableComponentType: availableChildComponentTypes) {
            if (componentType == availableComponentType) {
                return true;
            }
        }
        return false;
    }

    public static Class<? extends ComponentBase> getCorrespondingType(EComponentType componentType) {
        Class<? extends ComponentBase> targetType = null;
        switch (componentType) {
            case image:
                targetType = ComponentImage.class;
                break;
            case text:
                targetType = ComponentText.class;
                break;
            case banner:
                targetType = ComponentBanner.class;
                break;
            case productList:
                targetType = ComponentProductList.class;
                break;
            case feedList:
                targetType = ComponentFeedList.class;
                break;
        }
        return targetType;
    }
}
