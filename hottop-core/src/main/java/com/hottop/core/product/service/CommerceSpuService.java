package com.hottop.core.product.service;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.commerce.enums.ESpuStatus;
import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSkuSpecificationIndicator;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceSkuRepository;
import com.hottop.core.repository.commerce.CommerceSpuRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.RedisUtil;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommerceSpuService extends EntityBaseService<CommerceSpu, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceSpuRepository commerceSpuRepository;

    @Autowired
    private CommerceSkuRepository commerceSkuRepository;
    @Autowired
    private CommerceSkuService commerceSkuService;

    @Autowired
    private CommerceServiceService commerceServiceService;

    @Autowired
    private CommerceCategoryService commerceCategoryService;

    @Autowired
    private CommerceBrandService commerceBrandService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisConfig redisConfig;


    @Override
    public EntityBaseRepository<CommerceSpu, Long> repository() {
        return commerceSpuRepository;
    }


    //更新spu
    @Transactional
    public Response updateSpu(CommerceSpu commerceSpu, Long spuId) throws Exception {
        CommerceSpu spu_repo = findOne(CommerceSpu.class, spuId);
        if (spu_repo == null) {
            throw new Exception("更新商品不存在");
        }
        transferIfFieldsNotBlank(commerceSpu, spu_repo);
        CommerceSpu updated_spu = update(spu_repo);

        List<CommerceSku> skuList = commerceSpu.getSkus();
        Map<String, CommerceSku> skuMap = new HashMap<>();
        for (CommerceSku sku : skuList) {  // key:{尺码:xl,颜色:黑色} value:sku
            CommerceSkuSpecificationIndicator indicator = new CommerceSkuSpecificationIndicator(updated_spu.getSpecifications());
            indicator.putAll(sku.getIndicators());
            skuMap.put(indicator.skuKey(), sku);
        }
        List<CommerceSku> skus_repo = commerceSkuRepository.findAllBySpuId(spuId);
        Map<String, CommerceSku> skuMap_repo = new HashMap<>();
        for (CommerceSku sku_repo : skus_repo) {
            CommerceSkuSpecificationIndicator indicator = new CommerceSkuSpecificationIndicator(updated_spu.getSpecifications());
            indicator.putAll(sku_repo.getIndicators());
            skuMap_repo.put(indicator.skuKey(), sku_repo);
        }
        List<CommerceSku> skus_delete = skuMap_repo.entrySet().stream().filter(entry -> !skuMap.keySet().contains(entry.getKey()))
                .map(entry -> entry.getValue()).collect(Collectors.toList());//最新的skuIds 数据库中没有的，就是要删除的数据
        commerceSkuRepository.deleteAll(skus_delete);
        for (String skuKey : skuMap.keySet()) {  //遍历新传过来的skus
            logger.info("数据库中【{}】这条数据skuId：{}", skuMap_repo.keySet().contains(skuKey) ? "有" : "没有", skuKey);
            if (skuMap_repo.keySet().contains(skuKey)) {  //数据库有这条
                transferIfFieldsNotBlank(skuMap.get(skuKey), skuMap_repo.get(skuKey));
                commerceSkuService.update(skuMap_repo.get(skuKey));
            } else { //数据库没有这条数据，新增
                commerceSkuService.save(skuMap.get(skuKey));
            }
        }
        CommerceSpu spu_repo_updated = findOne(CommerceSpu.class, spuId);
        List<CommerceSku> skus_repo_updated = commerceSkuRepository.findAllBySpuId(spuId);
        spu_repo_updated.setSkus(skus_repo_updated);
        return ResponseUtil.updateSuccessResponse(CommerceSpu.class.getSimpleName(), spu_repo_updated);
    }

    //新增商品spu
    @Transactional
    public Response addSpu(CommerceSpu commerceSpu) {
        try {
            commerceSpu.setStatus(ESpuStatus.init.name());
            CommerceSpu save = save(commerceSpu);
            List<CommerceSku> commerceSkuList = commerceSpu.getSkus();
            for (CommerceSku sku : commerceSkuList) {
                sku.setSpuId(save.getId());
            }
            commerceSkuRepository.saveAll(commerceSkuList);
            return ResponseUtil.addSuccessResponse(CommerceSpu.class.getSimpleName());
        } catch (Exception e) {
            logger.info("spu新增失败");
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(CommerceSpu.class.getSimpleName());
    }

    /**
     * todo : 更改商品的状态
     */
    @Transactional
    public Response changeSpuStatus(ESpuStatus status, Long id) {
        try {
            logger.info("修改商品状态：{}", status);
            CommerceSpu spu_repo = findOne(CommerceSpu.class, id);
            if (spu_repo == null) {
                return ResponseUtil.notExistResponse(CommerceSpu.class.getSimpleName());
            }
            spu_repo.setStatus(status.name());
            update(spu_repo);
            return ResponseUtil.updateSuccessResponse(CommerceSpu.class.getSimpleName());
        } catch (Exception e) {
            logger.info("s");
            e.printStackTrace();
        }
        return ResponseUtil.updateFailResponse(CommerceSpu.class.getSimpleName());
    }

    //查看商品-详情
    public CommerceSpu getSpuDetail(Long id) {
        CommerceSpu spu = findOne(CommerceSpu.class, id);
        List<CommerceSku> skus = commerceSkuRepository.findAllBySpuId(id);
        spu.setSkus(skus);
        transferSpu(spu);
        transferInventory(spu);
        return spu;
    }

    //转换spu
    public void transferSpu(CommerceSpu spu) {
        transferStatus(spu);//转换状态
        transferService(spu);//转换服务列表
        transferCategoryId(spu);//转换分类id
        transferBrandId(spu);//转换品牌id
    }

    //转换商品的库存inventory
    private void transferInventory(CommerceSpu spu) {
        if (spu == null) return;
        List<CommerceSku> skus = spu.getSkus();
        Integer inventory = new Integer(0);
        for (CommerceSku sku : skus) {
            inventory = inventory + sku.getInventory();
        }
        spu.setInventory(inventory);
    }

    //根据不同的map，转换字段
    private void transferBrandId(CommerceSpu spu) {
        if (spu == null) return;
        Map<Long, String> brandNamesMap = (Map<Long, String>) commerceBrandService.getBrandNames().getData();
        if (brandNamesMap.containsKey(spu.getBrandId())) {
            spu.setBrandIdShowName(brandNamesMap.get(spu.getBrandId()));
        } else {
            spu.setBrandIdShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }

    //转换分类id
    private void transferCategoryId(CommerceSpu spu) {
        if (spu == null) return;
        Map<Long, String> categoryIdNameMap = commerceCategoryService.getCategoryIdNameMap();
        if (categoryIdNameMap.containsKey(spu.getCategoryId())) {
            spu.setCategoryIdShowName(categoryIdNameMap.get(spu.getCategoryId()));
        } else {
            spu.setBrandIdShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }

    //转换spu 服务service字段
    private void transferService(CommerceSpu spu) {
        if (spu == null) return;
        Map<Long, String> serviceMap = (Map<Long, String>) commerceServiceService.getServiceMap().getData();
        ArrayList<Long> servicesIds = spu.getServices();
        ArrayList<String> servicesName = new ArrayList<>();
        for (Long serviceId : servicesIds) {
            if (serviceMap.containsKey(serviceId)) {
                servicesName.add(serviceMap.get(serviceId));
            } else {
                spu.setBrandIdShowName(BaseConfiguration.getMessage("common.status.unknow"));
            }
        }
        spu.setServicesShowName(servicesName);
    }

    //转化spu 状态status字段
    private void transferStatus(CommerceSpu spu) {
        if (spu == null) return;
        Map<String, String> statusMap = getStatusMap();
        if (statusMap.containsKey(spu.getStatus())) {
            spu.setStatusShowName(statusMap.get(spu.getStatus()));
        } else {
            spu.setStatusShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }

    //获取spu statusMap
    private static Map<String, String> statusMap;

    public Map<String, String> getStatusMap() {
        if (statusMap == null) {
            loadMap();
        }
        return statusMap;
    }

    private void loadMap() {
        //国际化获得商品的状态
        statusMap = new LinkedHashMap<>();
        String prefix = "commerce.spu.status.";
        ESpuStatus[] values = ESpuStatus.values();
        for (ESpuStatus spuStatus : values) {
            statusMap.put(spuStatus.name(), BaseConfiguration.getMessage(spuStatus.getMsgCode()));
        }
    }

    private final static String MapOfspuIdAndSalePrice = "MapOfspuIdAndSalePrice";

    //获取spu的价格区间
    public String getSpuSalePriceSection(Long spuId) {
        if (!redisUtil.exists(MapOfspuIdAndSalePrice)) {
            redisUtil.hmSet(MapOfspuIdAndSalePrice, "spuId", "salePrice");
            redisUtil.keySetExpireTime(MapOfspuIdAndSalePrice, redisConfig.getExpireTime());
        }
        Object o = redisUtil.hmGet(MapOfspuIdAndSalePrice, String.valueOf(spuId));
        if (o != null) {
            return (String) o;
        }
        List<CommerceSku> allBySpuId = commerceSkuService.findAllBySpuId(spuId);
        OptionalLong max = allBySpuId.stream().mapToLong(CommerceSku::getSalePrice).max();
        OptionalLong min = allBySpuId.stream().mapToLong(CommerceSku::getSalePrice).min();
        if (min.isPresent() && max.isPresent()) {
            String salePrice = CommonUtil.fenToYuan(min.getAsLong()).toString()
                    + "-"
                    + CommonUtil.fenToYuan(max.getAsLong()).toString();
            redisUtil.hmSet(MapOfspuIdAndSalePrice, String.valueOf(spuId), salePrice);
            return salePrice;
        }
        return "";
    }


    private void transferIfFieldsNotBlank(CommerceSpu source, CommerceSpu destination) {
        if (source.getBrandId() != null) {
            destination.setBrandId(source.getBrandId());
        }
        if (source.getCategoryId() != null) {
            destination.setCategoryId(source.getCategoryId());
        }
        if (StringUtils.isNotBlank(source.getTitle())) {
            destination.setTitle(source.getTitle());
        }
        if (source.getPurchaseUnit() != null) {
            destination.setPurchaseUnit(source.getPurchaseUnit());
        }
        if (source.getInventory() != null) {
            destination.setInventory(source.getInventory());
        }
        if (StringUtils.isNotBlank(source.getMarketPrice())) {
            destination.setMarketPrice(source.getMarketPrice());
        }
        if (source.getCarousel() != null) {
            destination.setCarousel(source.getCarousel());
        }
        if (source.getInfo() != null) {
            destination.setInfo(source.getInfo());
        }
        if (source.getComparison() != null) {
            destination.setComparison(source.getComparison());
        }
        if (source.getServices() != null) {
            destination.setServices(source.getServices());
        }
        if (source.getSpecifications() != null) {
            destination.setSpecifications(source.getSpecifications());
        }
        if (source.getAttributes() != null) {
            destination.setAttributes(source.getAttributes());
        }
    }

    private void transferIfFieldsNotBlank(CommerceSku source, CommerceSku destination) {
        if (source.getSpuId() != null) {
            destination.setSpuId(source.getSpuId());
        }
        if (source.getSalePrice() != null) {
            destination.setSalePrice(source.getSalePrice());
        }
        if (source.getMarketPrice() != null) {
            destination.setMarketPrice(source.getMarketPrice());
        }
        if (source.getInventory() != null) {
            destination.setInventory(source.getInventory());
        }
        if (source.getPurchaseUnit() != null) {
            destination.setPurchaseUnit(source.getPurchaseUnit());
        }
        if (StringUtils.isNotBlank(source.getSubtitle())) {
            destination.setSubtitle(source.getSubtitle());
        }
        if (source.getIndicators() != null) {
            destination.setIndicators(source.getIndicators());
        }
        if (source.getSubImg() != null) {
            destination.setSubImg(source.getSubImg());
        }
    }


}
