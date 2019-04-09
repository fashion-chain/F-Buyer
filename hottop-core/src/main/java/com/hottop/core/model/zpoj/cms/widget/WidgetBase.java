package com.hottop.core.model.zpoj.cms.widget;

import com.hottop.core.model.zpoj.cms.IWidget;
import com.hottop.core.model.zpoj.cms.enums.EWidgetType;

import java.io.Serializable;

public abstract class WidgetBase implements IWidget, Serializable {
    private EWidgetType widgetType;

    public WidgetBase(EWidgetType widgetType) {
        this.widgetType = widgetType;
    }

    @Override
    public EWidgetType getWidgetType() {
        return this.widgetType;
    }
}
