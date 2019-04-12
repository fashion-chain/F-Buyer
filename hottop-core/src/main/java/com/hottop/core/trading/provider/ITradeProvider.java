package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;

public interface ITradeProvider {
    ETradeProvider provider();

    String[] requiredParamKeys();

    String[] optionalParamKeys();
}
