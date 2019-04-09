package com.hottop.core.trading.provider;

import com.hottop.core.model.merchant.enums.ETradeProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:trade.properties")
@ConfigurationProperties(prefix = "wx.provider")
public class WxProvider implements ITradeProvider {
    private String appId;
    private String mchId;

    public WxProvider() {
    }

    @Override
    public ETradeProvider getProvider() {
        return ETradeProvider.wx;
    }
}
