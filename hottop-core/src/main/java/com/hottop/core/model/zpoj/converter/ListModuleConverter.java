package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.user.ModuleBase;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListModuleConverter extends GsonAttributeConverter<List<ModuleBase>> {
    @Override
    Type getType() {
        return new TypeToken<ArrayList<ModuleBase>>(){}.getType();
    }
}
