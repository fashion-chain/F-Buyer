package com.hottop.core.response.exclusion;

import com.google.gson.ExclusionStrategy;

import java.util.ArrayList;
import java.util.List;

public class BaseExclusionStrategyFactory {
    public static ExclusionStrategy[] createApiExclusionStrategies() {
        List<ExclusionStrategy> strategies = new ArrayList<>();
        return strategies.toArray(new ExclusionStrategy[strategies.size()]);
    }
}







