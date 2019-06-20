package com.hottop.core.repository.merchant;

import com.hottop.core.model.merchant.MerchantTrade;
<<<<<<< HEAD
=======
import com.hottop.core.model.merchant.enums.ETradeProvider;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantTradeRepository extends EntityBaseRepository<MerchantTrade, Long> {
<<<<<<< HEAD
=======
    MerchantTrade findByTradeProviderAndOutTradeNo(ETradeProvider provider, String outTradeNo);

    MerchantTrade findByTradeProviderAndTradeNo(ETradeProvider provider, String tradeNo);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
