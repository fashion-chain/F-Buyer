package com.hottop.core.feature;

import com.hottop.core.feature.type.exception.TypeValueNotValidException;
import com.hottop.core.utils.LogUtil;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public interface IIndicator<T extends IMeta> {
    ArrayList<T> metas();

    String name();

    IIndicator<T> registerMeta(T... metas);

    default boolean valid(HashMap<String, Object> valueMapper) {
        boolean valid = true;
        for (IMeta meta: metas()) {
            if (valueMapper.containsKey(meta.key())) {
//                if (ClassUtils.isAssignableValue(meta.serializeClass(), valueMapper.get(meta.key()))) {
//                    valid = false;
//                    break;
//                }

                if (!meta.serializeClass().isAssignableFrom(valueMapper.get(meta.key()).getClass())) {
                    valid = false;
                    break;
                }
            } else {
                if (meta.isRequired()) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    default HashMap<String, Object> value(HashMap<String, Object> valueMapper) throws TypeValueNotValidException {
        if (valueMapper == null) {
            return null;
        }
        if (valid(valueMapper)) {
            HashMap<String, Object> typeParams = new HashMap<>();
            for (IMeta meta: metas()) {
                if (valueMapper.containsKey(meta.key())) {
                    typeParams.put(meta.key(), meta.serializeClass().cast(valueMapper.get(meta.key())));
                }
            }
            return typeParams;
        }
        throw new TypeValueNotValidException(String.format("type: %s value not valid.", name()));
    }
}
