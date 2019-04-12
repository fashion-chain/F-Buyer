package com.hottop.core.utils;

import com.hottop.core.model.user.Designer;
import com.hottop.core.model.user.UserAddress;

import java.lang.reflect.Field;

/**
 * 生成模板方法
 */
public class GenerateFunction {


    public static void generateTwoSampleObjectTransferFunction(Class source, Class destination) {
        StringBuffer result = new StringBuffer();
        String sourceName = source.getSimpleName();
        String destinationName = destination.getSimpleName();
        result.append("private void transferIfFieldsNotBlank(").append(sourceName).append(" ")
                .append("source")
                .append(", ").append(destinationName).append(" ")
                .append("destination")
                .append(") {\n");
        Field[] declaredFields = source.getDeclaredFields();
        Field[] declaredFields_destination = destination.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            //if(StringUtils.isNotBlank(source.getZipCode())){
            //destination.setZipCode(source.getZipCode());
            //        }
            Field field = declaredFields[i];
            String fieldName = field.getName();
            if (ifDestinationAlsoHasField(fieldName, declaredFields_destination) ) {
                String UpFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                result.append("\tif(StringUtils.isNotBlank(source.get" + UpFieldName + "())){\n");
                result.append("\t\tdestination.set" + UpFieldName + "(source.get" + UpFieldName + "());\n");
                result.append("\t}\n");
            }
        }
        result.append("}\n");
        System.out.println(result);
    }

    private static boolean ifDestinationAlsoHasField(String fieldName, Field[] fields) {
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void generateEntity2DtoFunction() {

    }

    public static void main(String[] args) {
        generateTwoSampleObjectTransferFunction(UserAddress.class, UserAddress.class);
    }
}
