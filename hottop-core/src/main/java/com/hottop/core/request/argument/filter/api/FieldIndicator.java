package com.hottop.core.request.argument.filter.api;

import com.hottop.core.model.commerce.enums.EAttributeType;
import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.utils.FieldIndicatorTypesUtil;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class FieldIndicator implements Serializable {
    private String fieldName;
    private String showName;

    private String explanation;

    private FilterFieldWidget widget;
    private List<String> render;

    FieldIndicator(String fieldName, String showName, FilterFieldWidget widget) {
        this.fieldName = fieldName;
        this.showName = showName;
        this.widget = widget;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setRender(List<String> render) {
        this.render = render;
    }

    @Getter
    public static class FieldIndicatorBuilder {
        private String fieldName;
        private String showName;
        private String explanation;
        private List<String> render = FieldIndicatorTypesUtil.INPUT;

        private FilterFieldWidget widget;

        FieldIndicatorBuilder(String fieldName, String showName, FilterFieldWidget widget) {
            this.fieldName = fieldName;
            this.showName = showName;
            this.widget = widget;
        }

        public static FieldIndicatorBuilder field(String fieldName, String showName, FilterFieldWidget widget) {
            return new FieldIndicatorBuilder(fieldName, showName, widget);
        }

        public FieldIndicatorBuilder explanation(String explanation) {
            this.explanation = explanation;
            return this;
        }

        public FieldIndicatorBuilder render(Class<Enum> typeEnum) {
            this.render = Arrays.asList(typeEnum.getEnumConstants()).stream().map(x -> x.name()).collect(Collectors.toList());
            return this;
        }

        public FieldIndicatorBuilder render(List<String> render) {
            this.render = render;
            return this;
        }

        public FieldIndicator create() {
            FieldIndicator indicator = new FieldIndicator(fieldName, showName, widget);
            indicator.setExplanation(getExplanation());
            indicator.setRender(getRender());
            return indicator;
        }
    }
}
