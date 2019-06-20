package com.hottop.retailSpu;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceRetailSku;
import com.hottop.core.model.commerce.CommerceRetailSpu;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSkuSpecificationIndicator;
import com.hottop.core.product.service.CommerceSpuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRetailSpu {

    public static void main(String[] args) {

    }

    @Autowired
    private CommerceSpuService commerceSpuService;

    @Test
    public void createRetailSpu () {
        CommerceRetailSpu retailSpu = new CommerceRetailSpu();
        retailSpu.setSpuId(8l);
        retailSpu.setAppId(1l);
        retailSpu.setMarketPrice("20000");
        retailSpu.setRemark("零售商品备注");

        CommerceSpu spu = commerceSpuService.findOne(CommerceSpu.class, 8l);

        List<CommerceRetailSku> retailSkus = new ArrayList<>();
        CommerceRetailSku retailSku1 = new CommerceRetailSku();

        CommerceSkuSpecificationIndicator indicator = new CommerceSkuSpecificationIndicator(spu.getSpecifications());
        HashMap<String, String> skuIndicatorMap = new HashMap<>();
        skuIndicatorMap.put("颜色分类", "官方标配");
        indicator.putAll(skuIndicatorMap);
        retailSku1.setIndicators(indicator);
        retailSku1.setInventory(50l);
        retailSku1.setRemark("零售商品sku 备注");
        retailSku1.setSalePrice(50000l);

        retailSkus.add(retailSku1);

        retailSpu.setSkus(retailSkus);

        System.out.println(BaseConfiguration.generalGson().toJson(retailSpu));
    }
}
