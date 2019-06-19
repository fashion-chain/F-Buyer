package com.hottop.core.model.commerce;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.ListLongConverter;
import com.hottop.core.model.zpoj.converter.MediaConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Data
public class CommerceSeries extends EntityBase {
    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '系列名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '顶部背景图' ")
    @Convert(converter = MediaConverter.class)
    private Image topBackground;

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '系列发布人ID' ")
    private Long userId;

    @Column(columnDefinition = "JSON COMMENT '系列商品ID' ")
    @Convert(converter = ListLongConverter.class)
    private ArrayList<Long> spuIds;

    @Column(columnDefinition = "VARCHAR(1023) COMMENT '获取商品链接' ")
    private String url;

    //系列新增说明（description）字段
    @Column(columnDefinition = "VARCHAR(127) COMMENT '系列说明'")
    private String description;
}
