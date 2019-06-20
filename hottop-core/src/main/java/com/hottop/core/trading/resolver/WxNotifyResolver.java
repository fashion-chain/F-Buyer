package com.hottop.core.trading.resolver;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.trading.provider.WxProvider;

import java.util.Map;

public class WxNotifyResolver implements INotifyResolver {
    private WxProvider provider;

    public WxNotifyResolver(WxProvider provider) {
        this.provider = provider;
    }

    @Override
    public ETradeProvider provider() {
        return ETradeProvider.wx;
    }

    @Override
    public Object resolve(MerchantTrade trade, Map<String, String> params) throws Exception {

        return null;
    }
}
