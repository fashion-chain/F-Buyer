package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSkuSpecificationIndicator;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommerceSkuSpecificationIndicatorConverter extends GsonAttributeConverter<CommerceSkuSpecificationIndicator> {

    @Override
    Type getType() {
        return new TypeToken<CommerceSkuSpecificationIndicator>(){}.getType();
    }
}
