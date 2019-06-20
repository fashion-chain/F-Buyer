package com.hottop.core.model.zpoj;

import com.hottop.core.feature.type.IFeatureType;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '删除时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public boolean isDeleted() {
        return this.deleteTime != null;
    }

    private void featureType() throws Exception {
        for (Class<?> inter: this.getClass().getInterfaces()) {
            if (IFeatureType.class.isAssignableFrom(inter)) {
                ((IFeatureType)this).setTypeMeta(((IFeatureType)this).specificTypeMeta());
            }
        }
    }

    @PrePersist
    public void prePersist() throws Exception {
        featureType();
    }

    @PreUpdate
    public void preUpdate() throws Exception {
        featureType();
    }
}
