package com.hottop.core.trading.handler;

<<<<<<< HEAD
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.provider.WxProvider;

import javax.servlet.http.HttpServletRequest;

public class WxCnyHandler extends CnyHandlerBase<WxProvider> {

    public WxCnyHandler(WxProvider provider) {
        super(provider);
    }

    @Override
    public void payment(CnyCurrency currency) {
=======
import com.github.wxpay.sdk.WXPay;
import com.hottop.core.feature.status.EStatusEvent;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.response.exception.SimpleException;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.provider.WxProvider;
import com.hottop.core.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class WxCnyHandler extends HandlerBase<WxProvider, CnyCurrency> {

    private WxProvider provider;
    private CnyCurrency currency;

    public WxCnyHandler(MerchantTradeRepository merchantTradeRepository, WxProvider provider, CnyCurrency currency) {
        super(merchantTradeRepository);
        this.provider = provider;
        this.currency = currency;
    }

    @Override
    public CnyCurrency currency() {
        return currency;
    }

    @Override
    public WxProvider provider() {
        return provider;
    }

    @Override
    public Map<String, String> initOutParams(Trade trade) {
        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("appid", provider.getAppID());
        outParams.put("mch_id", provider.getMchID());
        outParams.put("nonce_str", trade.getTradeToken());
        outParams.put("body", trade.getDescription());
        outParams.put("out_trade_no", trade.getOutTradeNo());
        outParams.put("sign_type", "MD5");
        outParams.put("fee_type", "CNY");
        outParams.put("total_fee", String.valueOf(trade.getAmount()));
        outParams.put("spbill_create_ip", CommonUtil.getIpAddr(trade.getRequest()));
        outParams.put("notify_url", provider.getNotifyUrl());
        switch (trade.getTradeSource()) {
            case app:
                outParams.put("trade_type", "APP");
                break;
        }
        return outParams;
    }

    @Override
    public Response payment(MerchantTrade merchantTrade, Map<String, String> outParams) throws Exception {
        WXPay wxPay = new WXPay(provider, false);
        Map<String, String> respParams = wxPay.unifiedOrder(outParams);
        switch (respParams.get("return_code")) {
            case "FAIL":
                return Response.ResponseBuilder
                        .result(EResponseResult.ERROR_TRADE_WX_PAYMENT_ERROR)
                        .message(respParams.get("return_msg"))
                        .create();
            case "SUCCESS":
                merchantTrade.setReqParams(outParams);
                merchantTrade.setRespParams(respParams);
                merchantTrade.setStatus(StatusFactory.tracker(MerchantTrade.class, StatusFactory.getStatus(merchantTrade.getClass(), "pay", "prepay")));
                getMerchantTradeRepository().save(merchantTrade);
                if (StringUtils.equals(respParams.get("result_code"), "SUCCESS")
                        && StringUtils.equals(respParams.get("trade_type"), outParams.get("trade_type"))) {
                    Map<String, String> result = new HashMap<>();
                    result.put("prepay_id", respParams.get("prepay_id"));
                    return Response.ResponseBuilder
                            .result(EResponseResult.OK)
                            .data(result)
                            .create();
                }
                break;
            default:
                throw new SimpleException(this.getClass(), "wx payment error respParams return_code");
        }


        return null;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }
}
