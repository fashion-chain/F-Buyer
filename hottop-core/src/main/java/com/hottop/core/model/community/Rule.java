package com.hottop.core.model.community;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Rule extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '社区ID' ")
    private Long communityId;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL COMMENT '规则名' ")
    private String name;

    @Column(columnDefinition = "VARCHAR(1023) NOT NULL COMMENT '规则详情' ")
    private String content;
}
