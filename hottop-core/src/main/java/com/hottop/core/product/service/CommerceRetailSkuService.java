package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceRetailSku;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceRetailSkuRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//retailSku 零售商品sku service
@Service
public class CommerceRetailSkuService extends EntityBaseService<CommerceRetailSku, Long>{

    @Autowired
    private CommerceRetailSkuRepository commerceRetailSkuRepository;

    @Override
    public EntityBaseRepository<CommerceRetailSku, Long> repository() {
        return commerceRetailSkuRepository;
    }

    //根据retailSpuId关联查询sku
    public List<CommerceRetailSku> findAllByRetailSpuId(Long id){
        return commerceRetailSkuRepository.findAllByRetailSpuId(id);
    }
}
