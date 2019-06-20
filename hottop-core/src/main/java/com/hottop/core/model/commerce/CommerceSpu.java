package com.hottop.core.model.commerce;

<<<<<<< HEAD
=======
import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.commerce.enums.ESpuStatus;
import com.hottop.core.config.BaseConfiguration;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.commerce.bean.CommerceAttributeDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceServiceDto;
import com.hottop.core.model.zpoj.commerce.bean.CommerceSpecificationDto;
import com.hottop.core.model.zpoj.converter.*;

<<<<<<< HEAD
import lombok.Data;
=======
import com.hottop.core.request.argument.filter.EFilterOperator;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import com.hottop.core.request.argument.filter.api.FieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldWidget;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceIndicator;
import com.hottop.core.utils.FieldIndicatorTypesUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
<<<<<<< HEAD
import java.util.ArrayList;
=======
import javax.persistence.Transient;
import java.util.*;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

@Entity
@Data
public class CommerceSpu extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '品牌ID' ")
    private Long brandId;

<<<<<<< HEAD
    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '品类ID' ")
    private Long categoryId;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL DEFAULT '' COMMENT '商品标题' ")
    private String title;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '最小采购单元' ")
    private Integer purchaseUnit;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '库存' ")
    private Integer inventory;

    @Column(columnDefinition = "VARCHAR(63) DEFAULT '' COMMENT '售价' ")
    private String salePrice;

=======
    @Transient
    private String brandIdShowName;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '品类ID' ")
    private Long categoryId;

    @Transient
    private String categoryIdShowName;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL DEFAULT '' COMMENT '商品标题' ")
    private String title;

    @Column(columnDefinition = "VARCHAR(31) NOT NULL COMMENT '状态' ")
    private String status;

    @Transient
    private String statusShowName;

    @Transient
    private String statusMap;

    @Column(columnDefinition = "INT(11) NOT NULL DEFAULT 0 COMMENT '最小采购单元' ")
    private Integer purchaseUnit;

    //商品库存
    @Transient
    private Integer inventory;

    //显示市场价区间
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    @Column(columnDefinition = "VARCHAR(63) DEFAULT '' COMMENT '市场价' ")
    private String marketPrice;

    @Column(columnDefinition = "JSON NOT NULL COMMENT '轮播图' ")
    @Convert(converter = ListMediaConverter.class)
    private ArrayList<Media> carousel;

    @Column(columnDefinition = "JSON COMMENT '详情图' ")
    @Convert(converter = ListMediaConverter.class)
    private ArrayList<Media> info;

    @Column(columnDefinition = "JSON COMMENT '对照表' ")
<<<<<<< HEAD
    @Convert(converter = ListImageConverter.class)
    private ArrayList<Image> comparison;

    @Column(columnDefinition = "JSON COMMENT '商品服务' ")
    @Convert(converter = ListCommerceServiceDtoConverter.class)
    private ArrayList<CommerceServiceDto> services;
=======
    @Convert(converter = ListMediaConverter.class)
    private ArrayList<Image> comparison;

    @Column(columnDefinition = "JSON COMMENT '商品服务' ")
    @Convert(converter = ListLongConverter.class)
    private ArrayList<Long> services;

    @Transient
    private ArrayList<String> servicesShowName;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

    @Column(columnDefinition = "JSON COMMENT '商品规格' ")
    @Convert(converter = ListCommerceSpecificationDtoConverter.class)
    private ArrayList<CommerceSpecificationDto> specifications;

    @Column(columnDefinition = "JSON COMMENT '商品属性' ")
    @Convert(converter = ListCommerceAttributeDtoConverter.class)
    private ArrayList<CommerceAttributeDto> attributes;
<<<<<<< HEAD
=======

    @Transient
    private List<CommerceSku> skus;


    public static ProgramInterfaceIndicator getProgramInterfaceIndicator() throws Exception {

        return ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceSpu.class)
                .fieldIndicators(
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpu_.BRAND_ID,
                                BaseConfiguration.getMessage("commerce.brand.name"),
                                FilterFieldWidget.simpleInput())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(
                                CommerceSpu_.CATEGORY_ID,
                                BaseConfiguration.getMessage("commerce.category.name"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.cascadeTree).uri("/category/tree", RequestMethod.GET).create())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpu_.TITLE,
                                BaseConfiguration.getMessage("commerce.spu.title"),
                                FilterFieldWidget.simpleInput()).create(),
                        FieldIndicator.FieldIndicatorBuilder.field("status",
                                BaseConfiguration.getMessage("commerce.spu.status"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).uri("/spu/status", RequestMethod.GET).create())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpu_.PURCHASE_UNIT,
                                BaseConfiguration.getMessage("commerce.spu.purchaseUnit"),
                                FilterFieldWidget.simpleInput())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpu_.MARKET_PRICE,
                                BaseConfiguration.getMessage("commerce.spu.marketPrice"),
                                FilterFieldWidget.simpleInput())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpu_.CAROUSEL, //轮播图
                                BaseConfiguration.getMessage("commerce.spu.carousel"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.imageArray).uri("/imageUpload?to=product", RequestMethod.POST).create())
                                .render(FieldIndicatorTypesUtil.IMAGE)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("info",
                                BaseConfiguration.getMessage("commerce.spu.info"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.imageArray).uri("/imageUpload?to=product", RequestMethod.POST).create())
                                .render(FieldIndicatorTypesUtil.IMAGE)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("comparison",
                                BaseConfiguration.getMessage("commerce.spu.comparison"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.imageArray).uri("/imageUpload?to=product", RequestMethod.POST).create())
                                .render(FieldIndicatorTypesUtil.IMAGE)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("services",
                                BaseConfiguration.getMessage("commerce.spu.services"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.checkbox).uri("/service/list", RequestMethod.GET).create())
                                .render(FieldIndicatorTypesUtil.SERVICE)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("specifications",
                                BaseConfiguration.getMessage("commerce.spu.specifications"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("spuSpecificationChosen", 1).create())
                                .render(FieldIndicatorTypesUtil.SPECIFICATION)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("attributes",
                                BaseConfiguration.getMessage("commerce.spu.attributes"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("spuAttributeChosen", 1).create())
                                .render(FieldIndicatorTypesUtil.StrArray)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field("commerceSkuList",
                                BaseConfiguration.getMessage("commerce.spu.commerceSkuList"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("commerceSkuList", 1).create()
                        ).create()
                )
                .requiredFields(CommerceSpu_.BRAND_ID, CommerceSpu_.CATEGORY_ID, CommerceSpu_.TITLE,
                        "status", CommerceSpu_.PURCHASE_UNIT, CommerceSpu_.PURCHASE_UNIT,
                        CommerceSpu_.MARKET_PRICE, CommerceSpu_.INFO,
                        CommerceSpu_.SERVICES, CommerceSpu_.SPECIFICATIONS, CommerceSpu_.ATTRIBUTES)
                .sortableFields(CommerceSpu_.BRAND_ID, CommerceSpu_.CATEGORY_ID, CommerceSpu_.MARKET_PRICE)
                .filterFieldIndicators(
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceSpu_.CATEGORY_ID,
                                BaseConfiguration.getMessage("commerce.category.name"),
                                EFilterOperator.equal)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.cascadeTree).uri("/category/tree", RequestMethod.GET).create())
                                .create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceSpu_.BRAND_ID,
                                BaseConfiguration.getMessage("commerce.brand.name"),
                                EFilterOperator.equal)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).uri("/getByCids", RequestMethod.GET).create())
                                .create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField("status",
                                BaseConfiguration.getMessage("commerce.spu.status"),
                                EFilterOperator.equal)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).uri("/spu/status", RequestMethod.GET).create())
                                .create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField("inventory",
                                BaseConfiguration.getMessage("commerce.spu.inventory"),
                                EFilterOperator.greaterEqualThan, EFilterOperator.lessEqualThan)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.duration).create()).create()
                ).create();
    }

    public static Map<String, String> getStatusMap() {
        //国际化获得商品的状态
        HashMap<String, String> result = new HashMap<>();
        String prefix = "commerce.spu.status.";
        ESpuStatus[] values = ESpuStatus.values();
        for (ESpuStatus spuStatus : values) {
            result.put(spuStatus.name(), BaseConfiguration.getMessage(spuStatus.getMsgCode()));
        }
        return result;
    }

    //获取商品的图片，轮播图中的第一张
    public Image getSpuImage() {
        Image result = null;
        for (Media media : carousel) {
            if (media instanceof Media) continue;
            if (media instanceof Image) {
                result = (Image) media;
                break;
            }
        }
        return result;
    }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
