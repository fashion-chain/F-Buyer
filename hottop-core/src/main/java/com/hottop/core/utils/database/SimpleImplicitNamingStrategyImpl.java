package com.hottop.core.utils.database;

public class SimpleImplicitNamingStrategyImpl extends ImplicitNamingStrategyImpl {
    @Override
    public String getPrefix() {
        return "gb_";
    }

    @Override
    public String customize(String defaultTableEntityName) {
        return defaultTableEntityName;
    }
}
