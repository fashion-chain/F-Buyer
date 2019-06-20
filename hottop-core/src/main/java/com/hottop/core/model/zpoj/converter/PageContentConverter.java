package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.cms.bean.PageContent;

import java.lang.reflect.Type;

public class PageContentConverter extends GsonAttributeConverter<PageContent> {
    @Override
    Type getType() {
        return new TypeToken<PageContent>(){}.getType();
    }
}
