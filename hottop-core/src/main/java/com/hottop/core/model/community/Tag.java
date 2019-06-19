package com.hottop.core.model.community;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Tag extends EntityBase {
    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '社区ID' ")
    private Long communityId;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '标签名' ")
    private String tag;
}
