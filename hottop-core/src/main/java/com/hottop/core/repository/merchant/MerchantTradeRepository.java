package com.hottop.core.repository.merchant;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.merchant.enums.ETradeProvider;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantTradeRepository extends EntityBaseRepository<MerchantTrade, Long> {
    MerchantTrade findByTradeProviderAndOutTradeNo(ETradeProvider provider, String outTradeNo);

    MerchantTrade findByTradeProviderAndTradeNo(ETradeProvider provider, String tradeNo);
}
