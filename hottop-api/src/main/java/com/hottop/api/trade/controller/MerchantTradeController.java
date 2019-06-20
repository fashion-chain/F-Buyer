package com.hottop.api.trade.controller;

<<<<<<< HEAD
import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.trade.service.MerchantTradeService;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.order.service.PurchaseOrderService;
import com.hottop.api.trade.service.MerchantTradeService;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.model.merchant.enums.ETradeSource;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.TradeFactory;
import com.hottop.core.trading.provider.AliProvider;
import com.hottop.core.trading.provider.WxProvider;
import com.hottop.core.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.border.EtchedBorder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

@RequestMapping("/trade")
@RestController
public class MerchantTradeController extends ApiBaseController<MerchantTrade> {

    @Autowired
    private MerchantTradeService merchantTradeService;

<<<<<<< HEAD
=======
    @Autowired
    private MerchantTradeRepository merchantTradeRepository;

    @Autowired
    private TradeFactory tradeFactory;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping(value = "/wxNotify", method = RequestMethod.POST)
    public Object wxNotify(HttpServletRequest request) throws Exception {
        WXPay wxPay = new WXPay((WxProvider) tradeFactory.getProvider(ETradeProvider.wx));
        Map<String, String> params = WXPayUtil.xmlToMap(null);
        if (wxPay.isPayResultNotifySignatureValid(params)) {
            switch (params.get("return_code")) {
                case "SUCCESS":
                    return tradeFactory.resolve(ETradeProvider.wx, params.get("out_trade_no"), params.get("transaction_id"), params);
                case "FAIL":
                    throw new Exception(String.format("wxNotify return_code FAIL. out_trade_no: %s", params.get("out_trade_no")));
            }
            return null;
        } else {
            throw new Exception(String.format("wxNotify wxPay.isPayResultNotifySignatureValid invalid. out_trade_no: %s", params.get("out_trade_no")));
        }
    }

    @RequestMapping(value = "/aliNotify", method = RequestMethod.POST)
    public Object aliNotify(HttpServletRequest request) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliProvider.aliPublicKey, "utf-8","RSA2");
        if (signVerified) {
            return tradeFactory.resolve(ETradeProvider.ali, params.get("out_trade_no"), params.get("trade_no"), params);
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_TRADE_ALI_PAYMENT_ERROR)
                .create();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response test(HttpServletRequest request) throws Exception {
        Trade testTrade = Trade.TradeBuilder
                .init(request, ETradeProvider.wx, ETradeOperate.payment, ETradeSource.app)
                .currencyAmount(ECurrency.cny, 1L)
                .tradeTokenAndOutTradeNo(EncryptUtil.randomAlphaNumeric(32), EncryptUtil.randomAlphaNumeric(32))
                .info("this is subject", "this is descirption", "this is remark")
                .create();
        return tradeFactory.handle(testTrade);
    }

    //获取交易提供商
    private ETradeProvider getETradeProvider(String tradeProvider) {
        ETradeProvider result = null;
        try {
            result = ETradeProvider.valueOf(tradeProvider);
        }catch (IllegalArgumentException e){
            logger.info("{}：这种交易方式不存在", tradeProvider);
            throw new IllegalArgumentException("交易方式不存在");
        }
        return result;
    }
    //用户在收银台
    //选择微信支付，或者支付宝支付，点击确认支付
    @GetMapping(path = "/{tradeProvider}/{payTradeNo}")
    public Response wxPay(HttpServletRequest request,
                          @PathVariable("tradeProvider") String tradeProvider,
                          @PathVariable("payTradeNo") String payTradeNo) throws Exception {
        ETradeProvider eTradeProvider = getETradeProvider(tradeProvider);
        CommercePurchaseOrder order = purchaseOrderService.findByPayTradeNo(payTradeNo);
        Trade trade = Trade.TradeBuilder
                .init(request, eTradeProvider, ETradeOperate.payment, ETradeSource.app)
                .currencyAmount(ECurrency.cny, order.getPayAmount())
                .tradeTokenAndOutTradeNo(payTradeNo, payTradeNo)
                .info("subject", "description", "remark")
                .create();
        return tradeFactory.handle(trade);
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    @Override
    public Class<MerchantTrade> clazz() {
        return MerchantTrade.class;
    }

    @Override
    public EntityBaseService service() {
        return this.merchantTradeService;
    }
<<<<<<< HEAD
=======

    public static void main(String[] args) {
        ETradeProvider eTradeProvider = null;
        try {
            eTradeProvider = ETradeProvider.valueOf("ww");
        } catch (IllegalArgumentException e) {
            System.out.println("支付方式不存在");
        }
        if(eTradeProvider != null){
            System.out.println(eTradeProvider.name());
        } else {
            System.out.println("eTradeProvider为空");
        }

    }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
