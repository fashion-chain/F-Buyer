package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceSkuRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommerceSkuService extends EntityBaseService<CommerceSku, Long>{

    @Autowired
    private CommerceSkuRepository commerceSkuRepository;

    @Override
    public EntityBaseRepository<CommerceSku, Long> repository() {
        return commerceSkuRepository;
    }

    //扣减库存
    @Transactional
    public Response reduceInventory (Long id, Integer quantity) {
        Optional<CommerceSku> sku_repo_option = commerceSkuRepository.findById(id);
        if (!sku_repo_option.isPresent()) {
            Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("sku商品不存在").create();
        }
        CommerceSku sku_repo = sku_repo_option.get();
        Integer inventory = sku_repo.getInventory();
        if (inventory < quantity) {
            Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("sku商品库存不足").create();
        }
        try {
            sku_repo.setInventory(inventory - quantity);
            update(sku_repo);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .simpleMessage("扣减库存成功").create();
        }catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .simpleMessage("扣减库存失败").create();
    }

    //根据spuId查询skus
    List<CommerceSku> findAllBySpuId(Long spuId){
        return commerceSkuRepository.findAllBySpuId(spuId);
    }
}
