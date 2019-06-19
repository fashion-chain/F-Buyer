package com.hottop.core.model.user.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * 省份 城区 dto
 */
@Data
public class ProvinceCityDto {

    private String number;

    private String pNumber;

    private String level;

    private String name;

    private String mergerName;

    private String zipCode;

}
