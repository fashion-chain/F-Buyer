package com.hottop.api.purchaseOrder;


import com.google.gson.Gson;
import com.hottop.core.order.vo.PurchaseOrderVo;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.user.UserAddress;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @org.junit.Test
    public void testGeneratePurchaseOrder() {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(1l);

        ArrayList<CommercePurchaseOrderSku> skuList = new ArrayList<>();
        CommercePurchaseOrderSku sku = new CommercePurchaseOrderSku();
        sku.setSpuId(1l);
        sku.setSkuId(3l);
        sku.setQuantity(20);
        sku.setAmount(44000l);
        sku.setPayAmount(44000l);

        CommercePurchaseOrderSku sku2 = new CommercePurchaseOrderSku();
        sku2.setSpuId(1l);
        sku2.setSkuId(5l);
        sku2.setQuantity(10);
        sku2.setAmount(44000l);
        sku2.setPayAmount(44000l);

        skuList.add(sku);
        skuList.add(sku2);

        PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
        purchaseOrderVo.setUserAddress(userAddress);
        purchaseOrderVo.setCommercePurchaseOrderSkuList(skuList);
        System.out.println(new Gson().toJson(purchaseOrderVo));

    }

    @org.junit.Test
    public void testPurchaseStatus() throws Exception {
        Status mt_pay_prepay = StatusFactory.getStatus(MerchantTrade.class, "pay_prepay");
        System.out.println(mt_pay_prepay.status());
    }


}
