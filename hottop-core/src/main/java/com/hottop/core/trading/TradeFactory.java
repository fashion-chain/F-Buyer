package com.hottop.core.trading;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.Response;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeCurrencyException;
import com.hottop.core.trading.exception.TradeProviderException;
import com.hottop.core.trading.handler.AliCnyHandler;
import com.hottop.core.trading.handler.ITradingHandler;
import com.hottop.core.trading.handler.WxCnyHandler;
import com.hottop.core.trading.provider.AliProvider;
import com.hottop.core.trading.provider.ITradeProvider;
import com.hottop.core.trading.provider.WxProvider;
import com.hottop.core.trading.resolver.AliNotifyResolver;
import com.hottop.core.trading.resolver.INotifyResolver;
import com.hottop.core.trading.resolver.WxNotifyResolver;
import org.springframework.core.io.ResourceLoader;

import java.util.Map;

public class TradeFactory {

    private ResourceLoader resourceLoader;
    private MerchantTradeRepository merchantTradeRepository;

    public TradeFactory(ResourceLoader resourceLoader, MerchantTradeRepository merchantTradeRepository) {
        this.resourceLoader = resourceLoader;
        this.merchantTradeRepository = merchantTradeRepository;
    }

    public Object resolve(ETradeProvider provider, String outTradeNo, String tradeNo, Map<String, String> params) throws Exception {
        INotifyResolver resolver = getResolver(getProvider(provider));
        MerchantTrade trade = merchantTradeRepository.findByTradeProviderAndOutTradeNo(provider, outTradeNo);
        trade.setTradeNo(tradeNo);
        return resolver.resolve(trade, params);
    }

    public Response handle(Trade trade) throws Exception {
        ITradeProvider provider = getProvider(trade.getTradeProvider());
        ICurrency currency = getCurrency(trade.getCurrency(), trade.getAmount());
        ITradingHandler handler = getHandler(provider, currency);
        return handler.handleOperate(trade);
    }

    private INotifyResolver getResolver(ITradeProvider provider) {
        INotifyResolver resolver = null;
        switch (provider.provider()) {
            case wx:
                resolver = new WxNotifyResolver((WxProvider) provider);
                break;
            case ali:
                resolver = new AliNotifyResolver();
                break;
        }
        return resolver;
    }

    private ICurrency getCurrency(ECurrency currencyType, Long amount) throws TradeCurrencyException {
        ICurrency currency = null;
        switch (currencyType) {
            case cny:
                currency = new CnyCurrency(amount);
                break;
            default:
                throw new TradeCurrencyException(currencyType);
        }
        return currency;
    }

    public ITradeProvider getProvider(ETradeProvider tradeProvider) throws TradeProviderException {
        ITradeProvider provider = null;
        switch (tradeProvider) {
            case wx:
                provider = new WxProvider(resourceLoader);
                break;
            case ali:
                provider = new AliProvider();
                break;
            default:
                throw new TradeProviderException(tradeProvider);
        }
        return provider;
    }

    private ITradingHandler getHandler(ITradeProvider provider,
                                      ICurrency currency) {
        ITradingHandler handler = null;
        switch (provider.provider()) {
            case wx:
                switch (currency.currency()) {
                    case cny:
                        handler = new WxCnyHandler(merchantTradeRepository, (WxProvider) provider, (CnyCurrency) currency);
                        break;
                }
                break;
            case ali:
                switch (currency.currency()) {
                    case cny:
                        handler = new AliCnyHandler(merchantTradeRepository, (AliProvider) provider, (CnyCurrency) currency);
                        break;
                }
                break;
            default:
                break;
        }

        return handler;
    }

}
