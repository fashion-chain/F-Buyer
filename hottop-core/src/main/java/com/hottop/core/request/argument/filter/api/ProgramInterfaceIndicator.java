package com.hottop.core.request.argument.filter.api;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.request.argument.view.DataViewRegistration;
import com.hottop.core.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProgramInterfaceIndicator implements Serializable {
    private ArrayList<String> fields;
    private ArrayList<String> views;

    private ArrayList<FieldIndicator> fieldIndicators;
    private ArrayList<String> requiredFields;
    private ArrayList<String> sortableFields;
    private ArrayList<FilterFieldIndicator> filterFieldIndicators;


    public static class ProgramInterfaceIndicatorBuilder {
        private ArrayList<String> fields;
        private ArrayList<String> views;

        private ArrayList<FieldIndicator> fieldIndicators;
        private ArrayList<String> requiredFields;
        private ArrayList<String> sortableFields;
        private ArrayList<FilterFieldIndicator> filterFieldIndicators;

        private ProgramInterfaceIndicatorBuilder() {
            this.fields = new ArrayList<>();
            this.views = new ArrayList<>();

            this.fieldIndicators = new ArrayList<>();
            this.requiredFields = new ArrayList<>();
            this.sortableFields = new ArrayList<>();
            this.filterFieldIndicators = new ArrayList<>();
        }

        public static ProgramInterfaceIndicatorBuilder init(Class clazz) {
            ProgramInterfaceIndicatorBuilder builder = new ProgramInterfaceIndicatorBuilder();
            ArrayList<Field> fields = CommonUtil.fields(clazz);
            for (Field field: fields) {
                builder.fields.add(field.getName());
            }
            builder.views.addAll(((DataViewRegistration) BaseConfiguration.getBean("dataViewRegistration")).clazz(clazz).views());
            return builder;
        }

        public ProgramInterfaceIndicatorBuilder fieldIndicators(FieldIndicator... fieldIndicators) {
            this.fieldIndicators.addAll(Arrays.asList(fieldIndicators));
            return this;
        }

        public ProgramInterfaceIndicatorBuilder requiredFields(String... requiredFields) throws Exception{
            List<String> fields_tmp = Arrays.asList(requiredFields);
            HashMap<String, String> map_tmp = new HashMap<>();
            for(FieldIndicator fieldIndicator : fieldIndicators) {
                map_tmp.put(fieldIndicator.getFieldName(), fieldIndicator.getFieldName());
            }
            for(String field : fields_tmp) {
                if(!map_tmp.containsKey(field)) throw new Exception("fieldIndicator中没有该必填字段：" + field);
            }
            this.requiredFields.addAll(Arrays.asList(requiredFields));
            return this;
        }

        public ProgramInterfaceIndicatorBuilder sortableFields(String... sortableFields) {
            this.sortableFields.addAll(Arrays.asList(sortableFields));
            return this;
        }

        public ProgramInterfaceIndicatorBuilder filterFieldIndicators(FilterFieldIndicator... fieldIndicators) {
            this.filterFieldIndicators.addAll(Arrays.asList(fieldIndicators));
            return this;
        }

        public ProgramInterfaceIndicator create() {
            return new ProgramInterfaceIndicator(fields, views, fieldIndicators, requiredFields, sortableFields, filterFieldIndicators);
        }
    }
}
