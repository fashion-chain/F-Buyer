package com.hottop.core.repository.merchant;

import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantTradeRepository extends EntityBaseRepository<MerchantTrade, Long> {
}
