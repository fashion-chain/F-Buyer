package com.hottop.core.trading.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.currency.CnyCurrency;
import com.hottop.core.trading.provider.AliProvider;
import com.hottop.core.trading.provider.ITradeProvider;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.PrecisionUtil;

import java.util.HashMap;
import java.util.Map;

public class AliCnyHandler extends HandlerBase<AliProvider, CnyCurrency> {

    private static AlipayClient alipayClient;
    private AliProvider provider;
    private CnyCurrency currency;

    public AliCnyHandler(MerchantTradeRepository merchantTradeRepository, AliProvider provider, CnyCurrency currency) {
        super(merchantTradeRepository);
        this.provider = provider;
        this.currency = currency;
        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(provider.gatewayUrl(),
                    provider.appId(),
                    provider.appPrivateKey(),
                    "json",
                    "UTF-8",
                    provider.aliPublicKey(),
                    "RSA2");
        }
    }

    @Override
    public AliProvider provider() {
        return this.provider;
    }

    @Override
    public CnyCurrency currency() {
        return this.currency;
    }

    @Override
    public Map<String, String> initOutParams(Trade trade) {
        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("subject", trade.getSubject());
        outParams.put("description", trade.getDescription());
        outParams.put("out_trade_no", trade.getOutTradeNo());
        outParams.put("total_amount", PrecisionUtil.getYuanByCent(trade.getAmount()));
        outParams.put("notify_url", provider.getNotifyUrl());
        outParams.put("timeout_express", "15d"); //default 15d
        outParams.put("product_code", "QUICK_MSECURITY_PAY");

        return outParams;
    }

    @Override
    public Response payment(MerchantTrade merchantTrade, Map<String, String> outParams) throws Exception {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(outParams.get("subject"));
        model.setBody(outParams.get("description"));
        model.setTotalAmount(outParams.get("total_amount"));
        model.setOutTradeNo(outParams.get("out_trade_no"));
        model.setTimeoutExpress(outParams.get("timeout_express"));
        model.setProductCode(outParams.get("product_code"));
        request.setBizModel(model);
        request.setNotifyUrl(provider.getNotifyUrl());
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            merchantTrade.setReqParams(outParams);
            merchantTrade.setRespParams(response.getParams());
            getMerchantTradeRepository().save(merchantTrade);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(response.getBody())
                    .create();
        } catch (AlipayApiException ex) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_TRADE_ALI_PAYMENT_ERROR)
                    .simpleMessage(ex.getMessage())
                    .create();
        }
    }
}
