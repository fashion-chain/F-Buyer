package com.hottop.core.trading.provider;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.utils.EncryptUtil;

public class WxProvider implements ITradeProvider {

    private static String appId = BaseConfiguration.getProperty("wx.provider.appId");
    private static String mchId = BaseConfiguration.getProperty("wx.provider.mchId");

    private static String[] requiredParams = new String[] {
            "appid", "mch_id", "nonce_str", "sign", "body", "out_trade_no", "total_fee", "spbill_create_ip",
            "notify_url", "trade_type"
    };

    private static String[] optionalParams = new String[] {
            "device_info", "sign_type", "detail", "attach", "fee_type", "time_start", "time_expire",
            "goods_tag", "product_id", "limit_pay", "openid", "receipt", "scene_info"
    };

    @Override
    public ETradeProvider provider() {
        return ETradeProvider.wx;
    }

    @Override
    public String[] requiredParamKeys() {
        return requiredParams;
    }

    @Override
    public String[] optionalParamKeys() {
        return optionalParams;
    }

    public String nonceStr() {
        return EncryptUtil.randomAlphaNumeric(32);
    }

    public String outTradeNo() {
        return null;
    }

    public String body() {
        return null;
    }

    public String sign() {
        return null;
    }

    public String appId() {
        return appId;
    }

    public String mchId() {
        return mchId;
    }
}
