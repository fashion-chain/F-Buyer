package com.hottop.core.model.zpoj.converter;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.model.zpoj.bean.Rule;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListRuleConverter extends GsonAttributeConverter<ArrayList<Rule>> {
    @Override
    Type getType() {
        return new TypeToken<ArrayList<Rule>>(){}.getType();
    }
}
