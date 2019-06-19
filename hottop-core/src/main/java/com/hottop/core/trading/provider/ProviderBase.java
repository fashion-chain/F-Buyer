package com.hottop.core.trading.provider;

import com.hottop.core.config.BaseConfiguration;

import java.util.HashMap;
import java.util.Map;

public abstract class ProviderBase implements ITradeProvider {

    public abstract String getUri();

    @Override
    public String getNotifyUrl() {
        return String.format("http://%s%s", BaseConfiguration.currentProfileHost(), getUri());
    }
}
