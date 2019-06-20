package com.hottop.core.utils;

import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.model.commerce.enums.ESpecificationType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldIndicatorTypesUtil {

    public static List<String> INPUT = Arrays.asList("text");

    public static List<String> IMAGE = Arrays.asList("image");

    public static List<String> SERVICE = Arrays.asList(EServiceType.values()).stream().map(x -> x.name()).collect(Collectors.toList());

    public static List<String> SPECIFICATION = Arrays.asList(ESpecificationType.values()).stream().map(x -> x.name()).collect(Collectors.toList());

    public static List<String> StrArray = Arrays.asList("strArray");

}
