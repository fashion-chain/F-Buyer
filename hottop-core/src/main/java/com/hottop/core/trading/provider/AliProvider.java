package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;

public class AliProvider implements ITradeProvider {
    @Override
    public ETradeProvider getProvider() {
        return ETradeProvider.ali;
    }
}
