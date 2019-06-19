package com.hottop.core.model.community;

import com.hottop.core.model.community.bean.ESocialType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.BusinessEntityIndicator;
import com.hottop.core.model.zpoj.bean.EBusinessEntityType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;

@Entity
@Data
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"userId", "socialType", "entityType"})
})
public class Social extends EntityBase {

    @Column(columnDefinition = "INT(11) NOT NULL COMMENT '用户ID' ")
    private Long userId;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '社交类型' ")
    private ESocialType socialType;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '实体类型' ")
    private EBusinessEntityType entityType;

    @Column(columnDefinition = "JSON COMMENT '社交实体' ")
    private ArrayList<Long> entities;

}
