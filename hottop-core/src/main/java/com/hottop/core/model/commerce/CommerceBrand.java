package com.hottop.core.model.commerce;

import com.hottop.core.feature.type.IFeatureType;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.model.zpoj.converter.MapObjectConverter;
import com.hottop.core.model.zpoj.converter.MediaConverter;
import com.hottop.core.model.zpoj.converter.TypeIndicatorConverter;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.tree.Node;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Data
public class CommerceBrand extends EntityBase {

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '品牌名' ")
    private String name;

    //品牌分类所属的id
    @Column(columnDefinition = "BIGINT DEFAULT NULL COMMENT '商品分类id'")
    private Long categoryId;

    @Transient
    private ArrayList<Node> categoryIdTree;

    @Column(columnDefinition = "VARCHAR(63) NOT NULL COMMENT '品牌所属国家' ")
    private String country;

    @Column(columnDefinition = "VARCHAR(1023) COMMENT '品牌描述' ")
    private String description;

    @Column(columnDefinition = "JSON COMMENT '品牌头像' ")
    @Convert(converter = MediaConverter.class)
    private Media avatar;
}
