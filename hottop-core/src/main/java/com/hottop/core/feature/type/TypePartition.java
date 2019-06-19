package com.hottop.core.feature.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hottop.core.model.zpoj.converter.MapObjectConverter;
import com.hottop.core.model.zpoj.converter.TypeIndicatorConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.HashMap;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TypePartition implements Serializable {
    @Column(columnDefinition = "VARCHAR(31) NOT NULL DEFAULT '' COMMENT '类型' ")
    @Convert(converter = TypeIndicatorConverter.class)
    private TypeIndicator type;

    @Column(columnDefinition = "JSON COMMENT '类型meta信息' ")
    @Convert(converter = MapObjectConverter.class)
    private HashMap<String, Object> typeMeta;

    public String getType() {
        return type.name();
    }

    public void specificTypeMeta() throws Exception {
        this.typeMeta = type.value(typeMeta);
    }
}
