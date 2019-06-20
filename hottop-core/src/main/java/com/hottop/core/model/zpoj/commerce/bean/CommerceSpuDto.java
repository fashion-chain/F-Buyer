package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.converter.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class CommerceSpuDto implements Serializable {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String brandId;

    private String categoryId;

    private String title;

    private String status;

    private Map<String,String> statusOperation;

    private Integer purchaseUnit;

    private Integer inventory;

    private String salePrice;

    private String marketPrice;

    private ArrayList<Media> carousel;

    private ArrayList<Media> info;

    private ArrayList<Image> comparison;

    private ArrayList<String> services;

    private ArrayList<CommerceSpecificationDto> specifications;

    private ArrayList<CommerceAttributeDto> attributes;

    private List<CommerceSku> skus;


}
