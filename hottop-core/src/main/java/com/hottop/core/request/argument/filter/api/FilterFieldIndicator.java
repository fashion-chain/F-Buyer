package com.hottop.core.request.argument.filter.api;

import com.hottop.core.request.argument.filter.EFilterOperator;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
public class FilterFieldIndicator {
    private String fieldName;
    private String showName;
    private FilterFieldWidget widget;
    private ArrayList<String> allowedOperators;

    private FilterFieldIndicator(String fieldName, String showName, EFilterOperator[] allowedOperators, FilterFieldWidget widget) {
        this.fieldName = fieldName;
        this.showName = showName;
        this.allowedOperators = new ArrayList<>();
        for (EFilterOperator operator: allowedOperators) {
            this.allowedOperators.add(operator.operator());
        }
        this.widget = widget;
    }

    public static class FilterFieldIndicatorBuilder {
        private String fieldName;
        private String showName;
        private FilterFieldWidget widget;
        private EFilterOperator[] allowedOperators;

        private FilterFieldIndicatorBuilder(String fieldName, String showName, EFilterOperator... operators) {
            this.fieldName = fieldName;
            this.showName = showName;
            this.allowedOperators = operators;
        }

        public static FilterFieldIndicatorBuilder filterField(String fieldName, String showName, EFilterOperator... operators) {
            return new FilterFieldIndicatorBuilder(fieldName, showName, operators);
        }

        public FilterFieldIndicatorBuilder widget(FilterFieldWidget widget) {
            this.widget = widget;
            return this;
        }

        public FilterFieldIndicator create() {
            FilterFieldIndicator indicator = new FilterFieldIndicator(fieldName, showName, allowedOperators, widget);
            return indicator;
        }
    }
}
