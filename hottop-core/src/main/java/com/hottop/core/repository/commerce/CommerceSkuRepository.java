package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommerceSkuRepository extends EntityBaseRepository<CommerceSku, Long> {

    List<CommerceSku> findAllBySpuId(Long spuId);
}
