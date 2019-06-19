package com.hottop.api.commerce;

import com.google.gson.Gson;
import com.hottop.core.model.commerce.enums.ESpuStatus;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.commerce.bean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testSpu {

    @Test
    public void testStatusFactory() throws Exception {
        Status spuStatus_init = StatusFactory.getStatus(CommerceSpu.class, "spuStatus_init");
        StatusStatusTracker tracker = StatusFactory.tracker(CommerceSpu.class, spuStatus_init);
        Set<Enum> eStatusEvents = tracker.availableEvents();
        System.out.println("-----" + BaseConfiguration.generalGson().toJson(tracker));
        for (Enum e : eStatusEvents) {
            System.out.println("--event:" + BaseConfiguration.generalGson().toJson(e));
        }
    }

    public static void main(String[] args) throws Exception {
        //createSpu();
        createSpu_SSD();
    }



    @Test
    public static void createSpu() throws Exception {
        //------- spu ----------
        CommerceSpu commerceSpu = new CommerceSpu();
        commerceSpu.setStatus(ESpuStatus.init.name());
        commerceSpu.setBrandId(9l);
        commerceSpu.setCategoryId(4l);//西服
        commerceSpu.setTitle("三星 MZ-76E500 860EVO 500G SSD笔记本台式固态");
        commerceSpu.setPurchaseUnit(2);//最小采购单元，2件
        commerceSpu.setInventory(100);//库存
        String imgStr = "{\n" +
                "        \"width\": 640,\n" +
                "        \"height\": 480,\n" +
                "        \"type\": \"image\",\n" +
                "        \"url\": \"https://fashionet-test.oss-cn-beijing.aliyuncs.com/product/ef2dad035b1749afbe5754c3486255cb.jpg\",\n" +
                "        \"uuid\": \"ef2dad035b1749afbe5754c3486255cb\",\n" +
                "        \"format\": \"jpg\"\n" +
                "    }";
        Media image = new Gson().fromJson(imgStr, Image.class);
        ArrayList<Media> carousel = new ArrayList<>();
        carousel.add(image);
        carousel.add(image);
        commerceSpu.setCarousel(carousel);//轮播图
        commerceSpu.setInfo(carousel);//详情图
        ArrayList<Image> images = new ArrayList<>();
        Image image2 = new Gson().fromJson(imgStr, Image.class);
        images.add(image2);
        images.add(image2);
        commerceSpu.setComparison(images);//对比图
        ArrayList<Long> services = new ArrayList<>();
        services.add(4l);
        commerceSpu.setServices(services);//服务
        //规格
        ArrayList<CommerceSpecificationDto> specificationList = new ArrayList<>();
        CommerceSpecificationDto specification_color = new CommerceSpecificationDto("颜色", ESpecificationType.string, new ArrayList<>(Arrays.asList("白色,黑色".split(",")))  );
        CommerceSpecificationDto specification_size = new CommerceSpecificationDto("尺码", ESpecificationType.string, new ArrayList<>(Arrays.asList("L,XL,XXL,3XL".split(","))) );
        specificationList.add(specification_color);
        specificationList.add(specification_size);
        commerceSpu.setSpecifications(specificationList);
        //属性
        ArrayList<CommerceAttributeDto> attributeList = new ArrayList<>();
        CommerceAttributeDto commerceAttribute_mianLiao = new CommerceAttributeDto("面料", new ArrayList<>(Arrays.asList("棉")) );
        CommerceAttributeDto commerceAttribute_fengGe = new CommerceAttributeDto("风格", new ArrayList<>(Arrays.asList("商务休闲")));
        CommerceAttributeDto commerceAttribute_jiJie = new CommerceAttributeDto("季节", new ArrayList<>(Arrays.asList("四季")));
        attributeList.add(commerceAttribute_mianLiao);
        attributeList.add(commerceAttribute_fengGe);
        attributeList.add(commerceAttribute_jiJie);
        commerceSpu.setAttributes(attributeList);
        //-------- sku ---------
        ArrayList<CommerceSku> commerceSkuDtos = new ArrayList<>();
        CommerceSku commerceSkuDto = new CommerceSku();
        //commerceSkuDto.setSpuId(1l);
        commerceSkuDto.setSalePrice(43000l);
        commerceSkuDto.setMarketPrice(50000l);
        commerceSkuDto.setInventory(50);
        commerceSkuDto.setPurchaseUnit(2);
        commerceSkuDto.setSubtitle("商务男士西服套装男修身职业装正装上班工作西装结婚新郎礼服外套 黑色");
        CommerceSkuSpecificationIndicator indicator = new CommerceSkuSpecificationIndicator(specificationList);
        HashMap<String, String> skuIndicatorMap = new HashMap<>();
        skuIndicatorMap.put("颜色", "黑色");
        skuIndicatorMap.put("尺码", "XL");
        indicator.putAll(skuIndicatorMap);
        commerceSkuDto.setIndicators(indicator);
        commerceSkuDtos.add(commerceSkuDto);

        CommerceSku commerceSkuDto2 = new CommerceSku();
        commerceSkuDto2.setSpuId(1l);
        commerceSkuDto2.setSalePrice(43000l);
        commerceSkuDto2.setMarketPrice(50000l);
        commerceSkuDto2.setInventory(100);
        commerceSkuDto2.setPurchaseUnit(2);
        commerceSkuDto2.setSubtitle("商务男士西服套装男修身职业装正装上班工作西装结婚新郎礼服外套 白色");
        CommerceSkuSpecificationIndicator indicator2 = new CommerceSkuSpecificationIndicator(specificationList);
        HashMap<String, String> skuIndicatorMap2 = new HashMap<>();
        skuIndicatorMap2.put("颜色", "白色");
        skuIndicatorMap2.put("尺码", "XXL");
        indicator2.putAll(skuIndicatorMap2);
        commerceSkuDto2.setIndicators(indicator2);
        commerceSkuDtos.add(commerceSkuDto2);

        commerceSpu.setSkus(commerceSkuDtos);

        String s = new Gson().toJson(commerceSpu);
        System.out.println(s);

    }

    public static void createSpu_SSD() throws Exception {
        //------- spu ----------
        CommerceSpu commerceSpu = new CommerceSpu();
        commerceSpu.setStatus(ESpuStatus.init.name());
        commerceSpu.setBrandId(9l);
        commerceSpu.setCategoryId(4l);//西服
        commerceSpu.setTitle("三星 MZ-76E500 860EVO 500G SSD笔记本台式固态");
        commerceSpu.setPurchaseUnit(2);//最小采购单元，2件
        commerceSpu.setInventory(200);//库存
        String imgStr = "{\n" +
                "        \"width\": 640,\n" +
                "        \"height\": 480,\n" +
                "        \"type\": \"image\",\n" +
                "        \"url\": \"https://fashionet-test.oss-cn-beijing.aliyuncs.com/product/ef2dad035b1749afbe5754c3486255cb.jpg\",\n" +
                "        \"uuid\": \"ef2dad035b1749afbe5754c3486255cb\",\n" +
                "        \"format\": \"jpg\"\n" +
                "    }";
        Media image = new Gson().fromJson(imgStr, Image.class);
        ArrayList<Media> carousel = new ArrayList<>();
        carousel.add(image);
        carousel.add(image);
        commerceSpu.setCarousel(carousel);//轮播图
        commerceSpu.setInfo(carousel);//详情图
        ArrayList<Image> images = new ArrayList<>();
        Image image2 = new Gson().fromJson(imgStr, Image.class);
        images.add(image2);
        images.add(image2);
        commerceSpu.setComparison(images);//对比图
        ArrayList<Long> services = new ArrayList<>();
        services.add(4l);
        commerceSpu.setServices(services);//服务
        //规格
        ArrayList<CommerceSpecificationDto> specificationList = new ArrayList<>();
        CommerceSpecificationDto specification_color = new CommerceSpecificationDto("颜色分类", ESpecificationType.image, new ArrayList<>(Arrays.asList("官方标配,固态+9.5MM光驱支架".split(","))) );
        specificationList.add(specification_color);
        commerceSpu.setSpecifications(specificationList);
        //属性
        ArrayList<CommerceAttributeDto> attributeList = new ArrayList<>();
        CommerceAttributeDto commerceAttribute_mianLiao = new CommerceAttributeDto("硬盘容量",  new ArrayList<>(Arrays.asList("500G")) );
        CommerceAttributeDto commerceAttribute_fengGe = new CommerceAttributeDto("成色", new ArrayList<>(Arrays.asList("全新")));
        CommerceAttributeDto commerceAttribute_jiJie = new CommerceAttributeDto("尺寸", new ArrayList<>(Arrays.asList("2.5英寸")));
        attributeList.add(commerceAttribute_mianLiao);
        attributeList.add(commerceAttribute_fengGe);
        attributeList.add(commerceAttribute_jiJie);
        commerceSpu.setAttributes(attributeList);
        //-------- sku ---------
        ArrayList<CommerceSku> commerceSkuDtos = new ArrayList<>();
        CommerceSku commerceSkuDto = new CommerceSku();
        //commerceSkuDto.setSpuId(1l);
        commerceSkuDto.setSalePrice(43000l);
        commerceSkuDto.setMarketPrice(50000l);
        commerceSkuDto.setInventory(50);
        commerceSkuDto.setPurchaseUnit(2);
        commerceSkuDto.setSubtitle("官方标配");
        commerceSkuDto.setSubImg(image2);
        CommerceSkuSpecificationIndicator indicator = new CommerceSkuSpecificationIndicator(specificationList);
        HashMap<String, String> skuIndicatorMap = new HashMap<>();
        skuIndicatorMap.put("颜色分类", "官方标配");
        indicator.putAll(skuIndicatorMap);
        commerceSkuDto.setIndicators(indicator);
        commerceSkuDtos.add(commerceSkuDto);

        CommerceSku commerceSkuDto2 = new CommerceSku();
        commerceSkuDto2.setSpuId(1l);
        commerceSkuDto2.setSalePrice(43000l);
        commerceSkuDto2.setMarketPrice(50000l);
        commerceSkuDto2.setInventory(100);
        commerceSkuDto2.setPurchaseUnit(2);
        commerceSkuDto2.setSubtitle("固态+9.5MM光驱支架");
        commerceSkuDto2.setSubImg(image2);
        CommerceSkuSpecificationIndicator indicator2 = new CommerceSkuSpecificationIndicator(specificationList);
        HashMap<String, String> skuIndicatorMap2 = new HashMap<>();
        skuIndicatorMap2.put("颜色分类", "固态+9.5MM光驱支架");
        indicator2.putAll(skuIndicatorMap2);
        commerceSkuDto2.setIndicators(indicator2);
        commerceSkuDtos.add(commerceSkuDto2);

        commerceSpu.setSkus(commerceSkuDtos);

        String s = new Gson().toJson(commerceSpu);
        System.out.println(s);

    }
}
