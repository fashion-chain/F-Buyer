package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceServiceDto;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListCommerceAttributeDtoConverter extends GsonAttributeConverter<ArrayList<CommerceAttributeDto>> {

    @Override
    Type getType() {
        return new TypeToken<ArrayList<CommerceAttributeDto>>(){}.getType();
    }
}
