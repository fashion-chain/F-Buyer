package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListCommerceSpecificationDtoConverter extends GsonAttributeConverter<ArrayList<CommerceSpecificationDto>> {

    @Override
    Type getType() {
        return new TypeToken<ArrayList<CommerceSpecificationDto>>(){}.getType();
    }
}
