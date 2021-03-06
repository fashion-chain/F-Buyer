package com.hottop.backstage.merchant.service;

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
public class MerchantTradeService extends EntityBaseService<MerchantTrade, Long> {

    @Autowired
    private MerchantTradeRepository merchantTradeRepository;

    @Autowired
    private TradeFactory tradeFactory;

    @Override
    public EntityBaseRepository<MerchantTrade, Long> repository() {
        return this.merchantTradeRepository;
    }
}
