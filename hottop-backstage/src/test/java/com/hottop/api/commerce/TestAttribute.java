package com.hottop.api.commerce;

import com.google.gson.Gson;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;

import java.util.ArrayList;
import java.util.Arrays;

public class TestAttribute {

    public static void main(String[] args) {
        newAttribute();
    }

    public static void newAttribute() {
        CommerceAttributeDto commerceAttribute_lingXing = new CommerceAttributeDto("领型", new ArrayList<>(Arrays.asList("立领,其它")) );
        System.out.println(new Gson().toJson(commerceAttribute_lingXing));
    }
}
