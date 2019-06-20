package com.hottop.api.commerce;

import com.google.gson.Gson;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceService;
import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.repository.commerce.CommerceServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {

    public static void main(String[] args) {
        newService();
    }

    public static void newService() {
        //1. name String 服务名称
        //2. description 服务描述
        //3. type        服务类型 enum (normal,)
        //4. icon        服务图片地址 json
        CommerceService commerceService = new CommerceService();
        commerceService.setName("厂家服务");
        commerceService.setDescription("本产品全国联保，享受三包服务，质保期为：1年质保如因质量问题或故障，凭厂商维修中心或特约维修点的质量检测证明，享受7日内退货，15日内换货，15日以上在质保期内享受免费保修等三包服务！");
        commerceService.setType(EServiceType.normal);
        String icon = "{\n" +
                "        \"width\": 720,\n" +
                "        \"height\": 509,\n" +
                "        \"type\": \"image\",\n" +
                "        \"url\": \"iconService/30ca36bdb2364dff90d4ecf9349d19c3.jpg\",\n" +
                "        \"uuid\": \"30ca36bdb2364dff90d4ecf9349d19c3\",\n" +
                "        \"format\": \"jpg\"\n" +
                "    }";
        Image image = new Gson().fromJson(icon, Image.class);
        commerceService.setIcon(image);
        System.out.println(new Gson().toJson(commerceService));
    }

    @Autowired
    private CommerceServiceRepository commerceServiceRepository;

    @Test
    public void testFindById() {
        List<CommerceService> services = commerceServiceRepository.findAllByDeleteTimeIsNull();
        for(CommerceService service : services){
            System.out.println(BaseConfiguration.generalGson().toJson(service));
        }

    }


}
