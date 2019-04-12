package com.hottop.api.trade;

import com.hottop.core.model.merchant.enums.ECurrency;
import com.hottop.core.model.merchant.enums.ETradeOperate;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.TradeFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTest extends TestBase {

    @Autowired
    private TradeFactory tradeFactory;

    @Test
    public void testTrade() throws Exception {
        Trade testTrade = Trade.TradeBuilder
                .init(ETradeProvider.wx, ETradeOperate.payment)
                .param(Trade.ExtraKey.requiredKey("spbill_create_ip"), "1.1.1.1")
                .items(Trade.TradeItem.newItem(ECurrency.cny, 10L)
                        .param(Trade.ExtraKey.requiredKey("body"), "this is demo body"))
                .create();
        tradeFactory.handle(testTrade);
    }
}
