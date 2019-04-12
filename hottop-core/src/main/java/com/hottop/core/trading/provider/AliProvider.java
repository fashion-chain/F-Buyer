package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;

public class AliProvider implements ITradeProvider {
    @Override
    public ETradeProvider provider() {
        return ETradeProvider.ali;
    }

    @Override
    public String[] requiredParamKeys() {
        return new String[0];
    }

    @Override
    public String[] optionalParamKeys() {
        return new String[0];
    }
}
