package com.hottop.core.model.zpoj.cms.wrapper;

import com.hottop.core.model.zpoj.cms.template.WidgetTemplate;

import java.io.Serializable;
import java.util.ArrayList;

public class WidgetWrapper extends WrapperBase implements Serializable {
    private ArrayList<ComponentWrapper> components;
    private ArrayList<WidgetWrapper> widgets;

    public WidgetWrapper(WidgetTemplate template) {
        super(template);
    }


}
