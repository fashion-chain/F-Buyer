package com.hottop.core.request.argument.view;

import com.hottop.core.request.argument.field.FieldExporter;
import com.hottop.core.request.argument.view.exception.ViewNotFoundException;

import java.lang.reflect.Type;

public class ViewExporter extends FieldExporter {

    public static ViewExporter NO_VIEW_EXPORT = new ViewExporter();

    private ViewExporter() {
    }

    public ViewExporter(Class clazz, String viewName, DataViewRegistration registration) throws Exception {
        DataView dataView = registration.getView(clazz, viewName);
        init(clazz, dataView.getAttributes());
    }
}
