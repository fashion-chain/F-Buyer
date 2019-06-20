package com.hottop.core.trading.provider;

<<<<<<< HEAD
import com.hottop.core.model.merchant.enums.ETradeProvider;

public class AliProvider implements ITradeProvider {
    @Override
    public ETradeProvider getProvider() {
        return ETradeProvider.ali;
    }
=======
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.merchant.enums.ETradeProvider;

public class AliProvider extends ProviderBase {

    private static String gatewayUrl = BaseConfiguration.getProperty("ali.provider.gatewayUrl");
    private static String appId = BaseConfiguration.getProperty("ali.provider.appId");
    private static String appPrivateKey = BaseConfiguration.getProperty("ali.provider.appPrivateKey");
    public static String aliPublicKey = BaseConfiguration.getProperty("ali.provider.aliPublicKey");

    @Override
    public String getUri() {
        return "/trade/aliNotify";
    }

    public String appPrivateKey() {
        return appPrivateKey;
    }

    public String aliPublicKey() {
        return aliPublicKey;
    }

    public String gatewayUrl() {
        return gatewayUrl;
    }

    public String appId() {
        return appId;
    }

    @Override
    public ETradeProvider provider() {
        return ETradeProvider.ali;
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
