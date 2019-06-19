package com.hottop.core.repository.commerce;

import com.hottop.core.model.commerce.CommerceRetailSku;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//retailSku 零售商品 repository
@Repository
public interface CommerceRetailSkuRepository extends EntityBaseRepository<CommerceRetailSku, Long>{

    List<CommerceRetailSku> findAllByRetailSpuId(Long id);
}
