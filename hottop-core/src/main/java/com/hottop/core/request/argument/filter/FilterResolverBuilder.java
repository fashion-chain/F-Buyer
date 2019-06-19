package com.hottop.core.request.argument.filter;

import com.hottop.core.model.zpoj.EntityBase;

import java.util.HashMap;

public class FilterResolverBuilder {
    private FilterStringBuilder filterStringBuilder;
    private HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper;

    private FilterResolverBuilder(FilterStringBuilder filterStringBuilder) {
        this.filterStringBuilder = filterStringBuilder;
    }

    public static FilterResolverBuilder init(FilterStringBuilder filterStringBuilder) {
        return new FilterResolverBuilder(filterStringBuilder);
    }

    public FilterResolverBuilder putParams(EFilterFunction function, String key, String value) {
        if (this.funcParametersMapper == null) {
            this.funcParametersMapper = new HashMap<>();
        }
        if (!this.funcParametersMapper.containsKey(function)) {
            this.funcParametersMapper.put(function, new HashMap<>());
        }
        this.funcParametersMapper.get(function).put(key, value);
        return this;
    }

    public <T extends EntityBase> IFilterResolver<T> create() {
        return FilterResolverFactory.create(filterStringBuilder.toString(), funcParametersMapper);
    }
}
