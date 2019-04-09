package com.hottop.core.model.zpoj.commerce.bean;

import com.hottop.core.BaseConstant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CommerceSkuSpecificationIndicator extends HashMap<String, String> implements Serializable {
    private transient ArrayList<CommerceSpecificationDto> specifications;

    public CommerceSkuSpecificationIndicator(ArrayList<CommerceSpecificationDto> specifications) {
        this.specifications = specifications;
    }

    public boolean valid() {
        boolean valid = false;
        for (Entry<String, String> entry : entrySet()) {
            boolean specificationValid = false, valueValid = false;
            for (CommerceSpecificationDto specification : specifications) {
                if (StringUtils.equals(entry.getKey(), specification.getName())) {
                    specificationValid = true;
                    if (specification.getRecommendationValues().contains(entry.getValue())) {
                        valueValid = true;
                    }
                    break;
                }
            }
            valid = specificationValid && valueValid;
            if (!valid) {
                break;
            }
        }
        return valid;
    }

    public String skuKey() {
        if (!valid())
            return null;
        ArrayList<String> keys = new ArrayList<>();
        for (CommerceSpecificationDto specificationDto: specifications) {
            keys.add(get(specificationDto.getName()));
        }
        return StringUtils.join(keys, BaseConstant.Common.SKU_KEY_SPLITTER);
    }
}
