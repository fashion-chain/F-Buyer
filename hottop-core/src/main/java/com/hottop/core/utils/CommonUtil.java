package com.hottop.core.utils;

import com.hottop.core.model.zpoj.EntityBase;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class CommonUtil {

    public static ArrayList<Field> fields(Class clazz) {
        return fields(clazz, EntityBase.class);
    }

    public static ArrayList<Field> fields(Class clazz, Class endClazz) {
        Class cls = clazz;
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        while(cls != Object.class) {
            cls = cls.getSuperclass();
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            if (endClazz != null && cls == endClazz) {
                break;
            }
        }
        return fields;
    }

    public static void print(String... messages) {
        for (String message: messages) {
            System.out.print(message);
        }
    }

    public static void println(String... messages) {
        for (String message: messages) {
            System.out.println(message);
        }
    }

    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    public static String getTableName(EntityManager em, Class<?> entityClass) {
        Metamodel meta = em.getMetamodel();
        EntityType<?> entityType = meta.entity(entityClass);

        Table t = entityClass.getAnnotation(Table.class);

        String tableName = (t == null)
                ? entityType.getName()
                : t.name();
        return tableName;
    }

    public static String printStackTraceElements(StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element: elements) {
            stringBuilder.append(element.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
