package com.hottop.core.trading.handler;

<<<<<<< HEAD
import com.hottop.core.model.merchant.enums.ETradeOperate;
=======
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.response.Response;
import com.hottop.core.trading.Trade;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.trading.currency.ICurrency;
import com.hottop.core.trading.exception.TradeHandlerException;
import com.hottop.core.trading.provider.ITradeProvider;

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;
=======
import java.util.Map;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

public interface ITradingHandler<T extends ITradeProvider, Y extends ICurrency> {
    T provider();

<<<<<<< HEAD
    void handleOperate(ETradeOperate operate, Y currency) throws TradeHandlerException;

    default void payment(Y currency) {}

    default void refund(Y currency) {}

    default void withdraw(Y currency) {}
=======
    Y currency();

    Map<String, String> initOutParams(Trade trade);

    Response handleOperate(Trade trade) throws Exception;

    default Response payment(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response refund(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response withdraw(MerchantTrade trade, Map<String, String> outParams) throws Exception {
        return null;
    }

    default Response queryTrade(String outTradeNo) throws Exception {
        return null;
    }

    default Response queryRefund(String outTradeNo) throws Exception {
        return null;
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
