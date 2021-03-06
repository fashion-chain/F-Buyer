package com.hottop.backstage.test.service;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.merchant.MerchantTradeRepository;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.trading.Trade;
import com.hottop.core.trading.TradeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TestMerchantTradeService extends EntityBaseService<MerchantTrade, Long> {

    @Autowired
    private MerchantTradeRepository merchantTradeRepository;

    @Autowired
    private TradeFactory tradeFactory;

    public void handleTrade(HttpServletRequest request, Trade trade) throws Exception {
<<<<<<< HEAD
        this.tradeFactory.handle(request, trade);
=======
        this.tradeFactory.handle(trade);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }

    @Override
    public EntityBaseRepository<MerchantTrade, Long> repository() {
        return this.merchantTradeRepository;
    }
}
