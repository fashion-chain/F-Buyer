package com.hottop.core.trading.resolver;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.order.service.PurchaseOrderService;
import com.hottop.core.utils.PrecisionUtil;
import com.hottop.core.utils.rabbitmq.PayProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class AliNotifyResolver implements INotifyResolver {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @Override
    public ETradeProvider provider() {
        return ETradeProvider.ali;
    }

    @Override
    public Object resolve(MerchantTrade trade, Map<String, String> params) {
        String appId = params.get("app_id");
        Long totalAmount = PrecisionUtil.getCentByYuan(params.get("total_amount"));
        if (StringUtils.equals(trade.getReqParams().get("app_id"), appId)
                && trade.getAmount().longValue() == totalAmount.longValue()) {
            switch (params.get("trade_status")) {
                case "WAIT_BUYER_PAY":
                    break;
                case "TRADE_CLOSED":
                    break;
                case "TRADE_SUCCESS":
                    purchaseOrderService.changePurchaseOrderStatusAsSuccess(trade.getOutTradeNo());
                    break;
                case "TRADE_FINISHED":
                    break;
            }
        }
        return null;
    }
}
