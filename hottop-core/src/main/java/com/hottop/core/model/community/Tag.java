package com.hottop.core.model.community;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Tag extends EntityBase {
<<<<<<< HEAD

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '标签名' ")
    private String tag;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '标签类型' ")
    private TagType type;

    public enum TagType {
        community,
        channel,
        forum
    }
=======
    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '社区ID' ")
    private Long communityId;

    @Column(columnDefinition = "VARCHAR(127) NOT NULL UNIQUE COMMENT '标签名' ")
    private String tag;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}
