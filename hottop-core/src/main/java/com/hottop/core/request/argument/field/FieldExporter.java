package com.hottop.core.request.argument.field;

import com.hottop.core.BaseConstant;
import com.hottop.core.request.argument.field.exception.FieldExistsException;
import com.hottop.core.request.argument.field.exception.FieldExporterException;
import com.hottop.core.request.argument.field.exception.FieldNotFoundException;
import com.hottop.core.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.metamodel.Attribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class FieldExporter {
    private transient ArrayList<String> fieldNames;

    public static FieldExporter NO_FIELD_EXPORT = new FieldExporter();

    public FieldExporter() {
        this.fieldNames = new ArrayList<>();
    }

    public boolean valid() {
        return this.fieldNames != null && this.fieldNames.size() > 0;
    }

    public FieldExporter init(Class clazz, ArrayList<Attribute> attributes) throws Exception {
        this.fieldNames.clear();
        for (Attribute attribute: attributes) {
            if (canAddThisField(clazz, attribute.getName())) {
                this.fieldNames.add(attribute.getName());
            }
        }
        return this;
    }

    public FieldExporter init(Class clazz, String fieldNamesString) throws Exception {
        this.fieldNames.clear();
        for (String fieldName: fieldNamesString.split(BaseConstant.Common.COMMA)) {
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            if (canAddThisField(clazz, fieldName)) {
                this.fieldNames.add(fieldName);
            }
        }
        return this;
    }

    public HashMap<String, Object> encapsulateExporter(Object object) throws FieldExporterException {
        try {
            HashMap<String, Object> result = new HashMap<>();
            for (Field field : CommonUtil.fields(object.getClass())) {
                if (shouldExportField(field)) {
                    field.setAccessible(true);
                    result.put(field.getName(), field.get(object));
                }
            }
            return result;
        } catch (IllegalAccessException ex) {
            throw new FieldExporterException("field access exception: " + ex.getMessage());
        }
    }

    private boolean shouldExportField(Field field) {
        for (String fieldName: fieldNames) {
            if (StringUtils.equals(field.getName(), fieldName)) {
                return true;
            }
        }
        return false;
    }

    private boolean canAddThisField(Class clazz, String fieldName) throws Exception {
        boolean classHasField = false, inExporterList;
        for (Field field: CommonUtil.fields(clazz)) {
            if (StringUtils.equals(field.getName(), fieldName)) {
                classHasField = true;
            }
        }
        if (!classHasField) {
            throw new FieldNotFoundException(clazz, fieldName);
        }
        inExporterList = this.fieldNames.contains(fieldName);
        if (inExporterList) {
            throw new FieldExistsException(clazz, fieldName);
        }
        return true;
    }
}
