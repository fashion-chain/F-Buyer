package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.model.zpoj.bean.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CommerceServiceDto implements Serializable {
    private EServiceType type;

    private String name;

    private String description;

    private Image icon;
}
