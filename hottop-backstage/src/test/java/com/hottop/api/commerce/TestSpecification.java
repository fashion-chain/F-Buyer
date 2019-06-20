package com.hottop.api.commerce;

import com.google.gson.Gson;
import com.hottop.core.model.commerce.CommerceSpecification;
import com.hottop.core.model.commerce.enums.ESpecificationType;

import java.util.ArrayList;
import java.util.Arrays;

public class TestSpecification {

    public static void main(String[] args) {
        createSpecification();
    }

    public static void createSpecification() {
        CommerceSpecification cp = new CommerceSpecification();
        //name 规格名 String
        //type 规格类型 枚举类型，选其中之一：image, string
        //recommendationValues 推荐值 List<String> 有哪些具体的规格
        cp.setName("颜色");
        cp.setType(ESpecificationType.string);
        cp.setRecommendationValues(new ArrayList<>(Arrays.asList("红色,白色,褐色,黑色,紫色,天蓝色,炫动蓝,宝强绿".split(","))));
        System.out.println(new Gson().toJson(cp));
    }
}
