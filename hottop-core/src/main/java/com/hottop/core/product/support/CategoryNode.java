package com.hottop.core.product.support;

import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CategoryNode {

    private Long key;

    private String name;

    private ArrayList<CommerceAttributeDto> attributes = new ArrayList<>();

    private ArrayList<CommerceSpecificationDto> specifications = new ArrayList<>();

    private ArrayList<CategoryNode> children = new ArrayList<>();
}
