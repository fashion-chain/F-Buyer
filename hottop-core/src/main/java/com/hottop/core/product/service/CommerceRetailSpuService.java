package com.hottop.core.product.service;

import com.hottop.core.config.RedisConfig;
import com.hottop.core.model.commerce.*;
import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderSkuRepository;
import com.hottop.core.repository.commerce.CommerceRetailSkuRepository;
import com.hottop.core.repository.commerce.CommerceRetailSpuRepository;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.RedisUtil;
import com.hottop.core.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

//retailSpu 零售商品 service
@Service
public class CommerceRetailSpuService extends EntityBaseService<CommerceRetailSpu, Long> {

    @Autowired
    private CommerceRetailSpuRepository commerceRetailSpuRepository;

    @Autowired
    private CommerceRetailSkuRepository commerceRetailSkuRepository;

    @Autowired
    private CommercePurchaseOrderSkuRepository commercePurchaseOrderSkuRepository;

    @Autowired
    private CommercePurchaseOrderRepository commercePurchaseOrderRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisConfig redisConfig;


    @Override
    public EntityBaseRepository<CommerceRetailSpu, Long> repository() {
        return commerceRetailSpuRepository;
    }

    //详情
    public CommerceRetailSpu getRetailSpuDetail(Long id) {
        CommerceRetailSpu retailSpu = findOne(CommerceRetailSpu.class, id);
        List<CommerceRetailSku> retailSkus = commerceRetailSkuRepository.findAllByRetailSpuId(id);
        retailSpu.setSkus(retailSkus);
        return retailSpu;
    }

    private final static String MapOfRetailSpuIdAndSalePrice = "MapOfRetailSpuIdAndSalePrice";

    //获取spu的价格区间
    public String getSalePriceSection(Long spuId) {
        if (!redisUtil.exists(MapOfRetailSpuIdAndSalePrice)) {
            redisUtil.hmSet(MapOfRetailSpuIdAndSalePrice, "retailSpuId", "salePrice");
            redisUtil.keySetExpireTime(MapOfRetailSpuIdAndSalePrice, redisConfig.getExpireTime());
        }
        Object o = redisUtil.hmGet(MapOfRetailSpuIdAndSalePrice, String.valueOf(spuId));
        if (o != null) {
            return (String) o;
        }
        List<CommerceRetailSku> allBySpuId = commerceRetailSkuRepository.findAllByRetailSpuId(spuId);
        OptionalLong max = allBySpuId.stream().mapToLong(CommerceRetailSku::getSalePrice).max();
        OptionalLong min = allBySpuId.stream().mapToLong(CommerceRetailSku::getSalePrice).min();
        if (min.isPresent() && max.isPresent()) {
            String salePrice = CommonUtil.fenToYuan(min.getAsLong()).toString()
                    + "-"
                    + CommonUtil.fenToYuan(max.getAsLong()).toString();
            redisUtil.hmSet(MapOfRetailSpuIdAndSalePrice, String.valueOf(spuId), salePrice);
            return salePrice;
        }
        return "";
    }

    //新增
    @Transactional
    public Response addRetailSpu(List<CommerceRetailSpu> retailSpus,
                                 User user) {
        try {
            CommerceRetailSpu retailSpu = null;
            for(CommerceRetailSpu r : retailSpus) {
                //采购单id
                Long purchaseOrderId = r.getPurchaseOrderId();
                retailSpu = new CommerceRetailSpu();
                retailSpu.setSpuId(r.getSpuId());
                retailSpu.setAppId(user.getAppId());
                retailSpu.setPurchaseOrderId(purchaseOrderId);
                CommerceRetailSpu saved = save(retailSpu);
                r.getSkus().stream().forEach(x -> x.setRetailSpuId(saved.getId()));
                commerceRetailSkuRepository.saveAll(r.getSkus());
            }
            return ResponseUtil.addSuccessResponse(CommerceRetailSpu.class.getSimpleName());
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.addFailResponse(CommerceRetailSpu.class.getSimpleName());
        }
    }
}
