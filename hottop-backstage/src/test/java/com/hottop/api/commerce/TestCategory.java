package com.hottop.api.commerce;

import com.google.gson.Gson;
import com.hottop.core.model.commerce.CommerceCategory;
import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  品类测试
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestCategory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    //public void init(){ mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); }

    public static void newCategory() {
        CommerceCategory category = new CommerceCategory();
        category.setName("服饰内衣");
        category.setParentId(0l);
        //规格
        ArrayList<CommerceSpecificationDto> specificationList = new ArrayList<>();
        CommerceSpecificationDto specification_color = new CommerceSpecificationDto("颜色", ESpecificationType.string, new ArrayList<>(Arrays.asList("白色,黑色,红色,绿色,橙黄色,天蓝色".split(",")))  );
        CommerceSpecificationDto specification_size = new CommerceSpecificationDto("尺码", ESpecificationType.string, new ArrayList<>(Arrays.asList("S,M,L,XL,XXL,3XL,4XL,5XL".split(","))) );
        specificationList.add(specification_color);
        specificationList.add(specification_size);
        category.setSpecifications(specificationList);
        //属性
        ArrayList<CommerceAttributeDto> attributeList = new ArrayList<>();
        CommerceAttributeDto commerceAttribute_mianLiao = new CommerceAttributeDto("面料", new ArrayList<>(Arrays.asList("棉,丝,其它".split(","))) );
        CommerceAttributeDto commerceAttribute_fengGe = new CommerceAttributeDto("风格", new ArrayList<>(Arrays.asList("商务休闲,青春休闲,欧美".split(","))));
        CommerceAttributeDto commerceAttribute_jiJie = new CommerceAttributeDto("季节", new ArrayList<>(Arrays.asList("春,夏,秋,冬,春秋，四季".split(","))));
        attributeList.add(commerceAttribute_mianLiao);
        attributeList.add(commerceAttribute_fengGe);
        attributeList.add(commerceAttribute_jiJie);
        category.setAttributes(attributeList);
        System.out.println(new Gson().toJson(category));
    }

    public static void newSecondCategory() {
        CommerceCategory category = new CommerceCategory();
        category.setName("男装");
        category.setParentId(1l);
        System.out.println(new Gson().toJson(category));
    }

    //update
    public static void thirdCategoryAddAttribute() {
        CommerceCategory category = new CommerceCategory();
        category.setName("风衣");
        category.setParentId(2l);
        //属性
        ArrayList<CommerceAttributeDto> attributeList = new ArrayList<>();
        CommerceAttributeDto commerceAttribute_xiaBaiSheJi = new CommerceAttributeDto("下摆设计", new ArrayList<>(Arrays.asList("直下摆,其它".split(","))) );
        CommerceAttributeDto commerceAttribute_kouDaiSheJi = new CommerceAttributeDto("口袋设计", new ArrayList<>(Arrays.asList("侧缝插袋,其它".split(","))));
        attributeList.add(commerceAttribute_xiaBaiSheJi);
        attributeList.add(commerceAttribute_kouDaiSheJi);

        category.setAttributes(attributeList);
        System.out.println(new Gson().toJson(category));
    }

    public static void newCategoryWithoutSpecificationAttribute() {
        //添加一级品类，个护化妆
        CommerceCategory cc = new CommerceCategory();
        cc.setName("个护化妆");
        cc.setParentId(0l);
        System.out.println(new Gson().toJson(cc));
    }

    public static void main(String[] args) {
        newCategory();
        newSecondCategory();
        thirdCategoryAddAttribute();
        newCategoryWithoutSpecificationAttribute();
    }
}





















