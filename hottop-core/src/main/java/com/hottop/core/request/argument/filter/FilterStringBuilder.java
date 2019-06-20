package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.model.zpoj.EntityBase;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterStringBuilder {
    private ArrayList<ArrayList<FilterParameterSpecification.ParameterCriteria>> orSpecifications;
    private ArrayList<FilterParameterSpecification.ParameterCriteria> andSpecifications;

    private FilterStringBuilder() {
        this.orSpecifications = new ArrayList<>();
    }

    public static FilterStringBuilder newBuilder() {
        return new FilterStringBuilder();
    }

    public FilterStringBuilder and(String key, EFilterOperator operator, String value) {
        if (this.andSpecifications == null) {
            this.andSpecifications = new ArrayList<>();
        }
        this.andSpecifications.add(new FilterParameterSpecification.ParameterCriteria(key, operator, value));
        return this;
    }

    public FilterStringBuilder or() {
        if (this.andSpecifications != null) {
            this.orSpecifications.add(new ArrayList<>(this.andSpecifications));
            this.andSpecifications.clear();
        }
        return this;
    }

    @Override
    public String toString() {
        ArrayList<String> orStrings = new ArrayList<>();
        for (ArrayList<FilterParameterSpecification.ParameterCriteria> andSpecifications: orSpecifications) {
            ArrayList<String> andStrings = new ArrayList<>();
            for (FilterParameterSpecification.ParameterCriteria parameterCriteria: andSpecifications) {
                andStrings.add(parameterCriteria.toString());
            }
            orStrings.add(String.join(BaseConstant.Common.FILTER_SPECIFICATION_AND_SPLITTER, andStrings.toArray(new String[]{})));
        }
        return String.join(BaseConstant.Common.FILTER_SPECIFICATION_OR_SPLITTER, orStrings.toArray(new String[]{}));
    }
}
