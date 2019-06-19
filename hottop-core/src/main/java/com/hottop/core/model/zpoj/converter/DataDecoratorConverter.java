package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.cms.bean.component.bean.DataDecorator;

import java.lang.reflect.Type;

public class DataDecoratorConverter extends GsonAttributeConverter<DataDecorator> {
    @Override
    Type getType() {
        return new TypeToken<DataDecorator>(){}.getType();
    }
}
