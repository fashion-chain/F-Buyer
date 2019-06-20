package com.hottop.core.trading.provider;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class WxProvider extends ProviderBase implements WXPayConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String appId = BaseConfiguration.getProperty("wx.provider.appId");
    private static String mchId = BaseConfiguration.getProperty("wx.provider.mchId");
    private static String paySignKey = BaseConfiguration.getProperty("wx.provider.paySignKey");
    private static InputStream certInputStream;

    public WxProvider(ResourceLoader resourceLoader) {
        try {
            if (certInputStream == null) {
                Resource resource = resourceLoader.getResource("classpath:wxpay_cert/apiclient_cert.p12");
                certInputStream = resource.getInputStream();
            }
        } catch (IOException ex) {
            logger.error(String.format("error loading wxpay_cert: %s", ex.getMessage()));
        }
    }

    @Override
    public String getUri() {
        return "/trade/wxNotify";
    }

    @Override
    public ETradeProvider provider() {
        return ETradeProvider.wx;
    }

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return paySignKey;
    }

    @Override
    public InputStream getCertStream() {
        return certInputStream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String s, long l, Exception e) {
                logger.info(String.format("domain: %s, elapsedTime: %s", s, l));
                if (e != null) {
                    logger.error(String.format("exception occurred: %s", e.getMessage()));
                }
            }

            @Override
            public DomainInfo getDomain(WXPayConfig wxPayConfig) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}
