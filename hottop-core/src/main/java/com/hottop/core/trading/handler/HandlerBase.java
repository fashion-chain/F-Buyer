package com.hottop.core.trading.handler;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.Response;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Getter
public abstract class HandlerBase<T extends ITradeProvider, Y extends ICurrency> implements ITradingHandler<T, Y> {

    private MerchantTradeRepository merchantTradeRepository;

    public HandlerBase(MerchantTradeRepository merchantTradeRepository) {
        this.merchantTradeRepository = merchantTradeRepository;
    }

    @Override
    public Response handleOperate(Trade trade) throws Exception {
        Map<String, String> outParams = initOutParams(trade);
        switch (trade.getTradeOperate()) {
            case payment:
                MerchantTrade merchantTrade = generate(trade);
                return payment(merchantTrade, outParams);
            case refund:
//                refund(merchantTrade, outParams);
                break;
            case withdraw:
//                withdraw(merchantTrade, outParams);
                break;
            default:
                throw new TradeHandlerException(this);
        }
        return null;
    }

    public MerchantTrade generate(Trade trade) throws Exception {
        MerchantTrade merchantTrade = new MerchantTrade();
        merchantTrade.setTradeProvider(trade.getTradeProvider());
        merchantTrade.setTradeOperate(trade.getTradeOperate());
        merchantTrade.setTradeSource(trade.getTradeSource());
        merchantTrade.setCurrency(trade.getCurrency());
        merchantTrade.setAmount(trade.getAmount());
        merchantTrade.setSubject(trade.getSubject());
        merchantTrade.setDescription(trade.getDescription());
        merchantTrade.setRemark(trade.getRemark());
        merchantTrade.setOutTradeNo(trade.getOutTradeNo());
        merchantTrade.setTradeToken(trade.getTradeToken());
        return merchantTrade;
    }
}
