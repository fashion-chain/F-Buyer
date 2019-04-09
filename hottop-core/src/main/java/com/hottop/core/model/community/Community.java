package com.hottop.core.model.community;

import com.hottop.core.model.zpoj.converter.ListLongConverter;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.ArrayList;

@Data
@Entity
public class Community extends Container {

    @Column(columnDefinition = "JSON COMMENT '社区管理员' ")
    @Convert(converter = ListLongConverter.class)
    private ArrayList<Long> moderators;

    @Column(columnDefinition = "JSON COMMENT '社区标签' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> tags;
}
