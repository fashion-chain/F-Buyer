package com.hottop.core.model.doll;

import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.feature.type.IFeatureType;
import com.hottop.core.feature.type.TypeFactory;
import com.hottop.core.feature.type.TypeIndicator;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import com.hottop.core.model.zpoj.converter.MapObjectConverter;
import com.hottop.core.model.zpoj.converter.StatusTrackerConverter;
import com.hottop.core.model.zpoj.converter.TypeIndicatorConverter;
import com.hottop.core.utils.LogUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Entity
@Data
public class Doll extends EntityBase implements IFeatureType {

    @Column(columnDefinition = "VARCHAR(63) COMMENT 'input类型string' ")
    private String inputString;

    @Column(columnDefinition = "INT(11) COMMENT 'input类型long' ")
    private Long inputLong;

    @Column(columnDefinition = "INT(11) COMMENT 'cascadeTree类型long' ")
    private Long cascadeTreeLong;

    //选中的select项的数组
    @Column(columnDefinition = "VARCHAR(63) COMMENT 'select类型values选择' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> selectValues;

    @Column(columnDefinition = "VARCHAR(63) COMMENT 'select类型api调用values' ")
    private String selectApiString;

    @Column(columnDefinition = "VARCHAR(63) COMMENT 'radio类型string' ")
    private String radioString;

    //字符串数组
    @Column(columnDefinition = "VARCHAR(63) COMMENT 'checkbox类型string' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> checkboxString;

    @Column(columnDefinition = "INT(11) COMMENT 'duration类型long' ")
    private Long durationLong;

    @Column(columnDefinition = "DATETIME COMMENT 'date类型Date' ")
    private Date dateDate;

    @Column(columnDefinition = "DATETIME COMMENT 'duration类型Date' ")
    private Date durationDateDate;

    @Column(columnDefinition = "VARCHAR(63) COMMENT 'status' ")
    @Convert(converter = StatusTrackerConverter.class)
    private StatusStatusTracker status;

    @Column(columnDefinition = "VARCHAR(31) DEFAULT '' COMMENT '类型' ")
    @Convert(converter = TypeIndicatorConverter.class)
    private TypeIndicator type;

    @Column(columnDefinition = "JSON COMMENT '类型meta信息' ")
    @Convert(converter = MapObjectConverter.class)
    private HashMap<String, Object> typeMeta;

}
