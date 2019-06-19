package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.cms.bean.TemplateContent;

import java.lang.reflect.Type;

public class TemplateContentConverter extends GsonAttributeConverter<TemplateContent> {
    @Override
    Type getType() {
        return new TypeToken<TemplateContent>(){}.getType();
    }
}
