package com.hottop.core.feature.type;

import com.hottop.core.BaseConstant;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory {
    private static HashMap<Class<?>, TypeCabinet> typeCabinetMapper = new HashMap<>();
    public static HashMap<String, Class<?>> symbolMapper = new HashMap<>();

    public static TypeIndicator dummyIndicator = new TypeIndicator("");

    public static TypeCabinet registerMap(Class<?> clazz, String symbol) {
        typeCabinetMapper.put(clazz, new TypeCabinet());
        symbolMapper.put(symbol, clazz);
        return typeCabinetMapper.get(clazz);
    }

    private static Class<?> getClassBySymbol(String typeString) {
        String[] typeParts = StringUtils.split(typeString, BaseConstant.Common.COMMON_SPLITTER);
        return symbolMapper.get(typeParts[0]);
    }

    public static TypeIndicator newType(Class<?> clazz, String typeName) {
        String symbol = null;
        for (Map.Entry<String, Class<?>> entry: symbolMapper.entrySet()) {
            if (clazz == entry.getValue()) {
                symbol = entry.getKey();
            }
        }
        return new TypeIndicator(String.format("%s%s%s", symbol, BaseConstant.Common.COMMON_SPLITTER, typeName));
    }

    public static TypeIndicator indicator(String fullTypeName) throws Exception {
        return typeCabinetMapper.get(getClassBySymbol(fullTypeName)).getItemByName(fullTypeName);
    }
}
