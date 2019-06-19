package com.hottop.core.trading.resolver;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeProvider;

import java.util.Map;

public interface INotifyResolver {
    ETradeProvider provider();

    Object resolve(MerchantTrade trade, Map<String, String> params) throws Exception;
}
