package com.hottop.core.request.argument.filter.api;

import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FilterFieldWidget {
    private EFilterWidgetType type;
    private String uri;
    private RequestMethod method;
    private HashMap<String, Object> params;
    private String hint;
    private String[] values;
    private HashMap<String, Object> extras;

    public static FilterFieldWidget simpleInput(String hint) {
        FilterFieldWidgetBuilder builder = FilterFieldWidgetBuilder.init(EFilterWidgetType.input);
        if (!StringUtils.isEmpty(hint)) {
            builder.hint(hint);
        }
        return builder.create();
    }

    public static FilterFieldWidget simpleInput() {
        return simpleInput("");
    }

    private FilterFieldWidget(EFilterWidgetType type, String uri, RequestMethod method, HashMap<String, Object> params, String hint, String[] values, HashMap<String, Object> extras) {
        this.type = type;
        this.uri = uri;
        this.method = method;
        this.params = params;
        this.hint = hint;
        this.values = values;
        this.extras = extras;
    }

    public static class FilterFieldWidgetBuilder {
        private EFilterWidgetType type;
        private String uri;
        private RequestMethod method;
        private HashMap<String, Object> params;
        private String hint;
        private String[] values;
        private HashMap<String, Object> extras;

        private FilterFieldWidgetBuilder(EFilterWidgetType type) {
            this.type = type;
        }

        public static FilterFieldWidgetBuilder init(EFilterWidgetType type) {
            return new FilterFieldWidgetBuilder(type);
        }

        public FilterFieldWidgetBuilder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public FilterFieldWidgetBuilder uri(String uri, RequestMethod method) {
            this.uri = uri;
            this.method = method;
            return this;
        }

        public FilterFieldWidgetBuilder uriParam(String key, String value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public FilterFieldWidgetBuilder values(String... values) {
            this.values = values;
            return this;
        }

        public FilterFieldWidgetBuilder extras(String key, Object value) {
            if (this.extras == null) {
                this.extras = new HashMap<>();
            }
            this.extras.put(key, value);
            return this;
        }

        public FilterFieldWidget create() {
            return new FilterFieldWidget(type, uri, method, params, hint, values, extras);
        }
    }
}
