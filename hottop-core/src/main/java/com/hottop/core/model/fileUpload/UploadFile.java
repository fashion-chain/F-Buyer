package com.hottop.core.model.fileUpload;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 上传文件表
 */
@Data
@Entity
public class UploadFile extends EntityBase {

    @Column(columnDefinition = "varchar(10) DEFAULT NULL COMMENT '上传文件类型'")
    private String fileType;

    @Column(columnDefinition = "JSON COMMENT '文件json String'")
    private String fileJson;

}
