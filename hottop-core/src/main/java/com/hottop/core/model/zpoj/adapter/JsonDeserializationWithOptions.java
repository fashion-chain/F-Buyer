package com.hottop.core.model.zpoj.adapter;

import com.google.gson.*;
import org.springframework.lang.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

public class JsonDeserializationWithOptions<T> implements JsonDeserializer<T> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldRequired {
    }

    /**
     * Called when the model is being parsed.
     *
     * @param je   Source json string.
     * @param type Object's model.
     * @param jdc  Unused in this case.
     *
     * @return Parsed object.
     *
     * @throws JsonParseException When parsing is impossible.
     * */
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        // Parsing object as usual.
        T pojo = new Gson().fromJson(je, type);

        // Getting all fields of the class and checking if all required ones were provided.
        checkFields(pojo.getClass().getDeclaredFields(), pojo);

        // Checking if all required fields of parent classes were provided.
        checkSuperClasses(pojo);

        // All checks are ok.
        return pojo;
    }

    /**
     * Checks whether all required fields were provided in the class.
     *
     * @param fields Fields to be checked.
     * @param pojo   Instance to check fields in.
     *
     * @throws JsonParseException When some required field was not met.
     * */
    private void checkFields(@NonNull Field[] fields, @NonNull Object pojo)
            throws JsonParseException {
        // Checking nested list items too.
        if (pojo instanceof List) {
            final List pojoList = (List) pojo;
            for (final Object pojoListPojo : pojoList) {
                checkFields(pojoListPojo.getClass().getDeclaredFields(), pojoListPojo);
                checkSuperClasses(pojoListPojo);
            }
        }

        for (Field f : fields) {

            try {
                f.setAccessible(true);
                if (f.get(pojo) instanceof List) {
                    final List pojoNestList = (List) f.get(pojo);
                    for (final Object pojoNestListPojo : pojoNestList) {
                        checkFields(pojoNestListPojo.getClass().getDeclaredFields(), pojoNestListPojo);
                        checkSuperClasses(pojoNestListPojo);
                    }
                }

                // If some field has required annotation.
                if (f.getAnnotation(FieldRequired.class) != null) {
                        // Trying to read this field's value and check that it truly has value.
//                        f.setAccessible(true);
                        Object fieldObject = f.get(pojo);
                        if (fieldObject == null) {
                            // Required value is null - throwing error.
                            throw new JsonParseException(String.format("%1$s -> %2$s",
                                    pojo.getClass().getSimpleName(),
                                    f.getName()));
                        } else {
                            checkFields(fieldObject.getClass().getDeclaredFields(), fieldObject);
                            checkSuperClasses(fieldObject);
                        }
                    }

                }
            // Exceptions while reflection.
            catch (IllegalArgumentException | IllegalAccessException e) {
                throw new JsonParseException(e);
            }
//
//
//            // If some field has required annotation.
//            if (f.getAnnotation(EnumRequired.class) != null) {
//                try {
//                    // Trying to read this field's value and check that it truly has value.
//                    f.setAccessible(true);
//                    Object fieldObject = f.get(pojo);
//                    if (!(fieldObject == null || !(fieldObject != null && fieldObject instanceof Class && ((Class<?>)fieldObject).isEnum()))) {
//                        throw new JsonParseException(String.format("%1$s -> %2$s is not an Enum",
//                                pojo.getClass().getSimpleName(),
//                                f.getName()));
//                    }
//                }
//
//                // Exceptions while reflection.
//                catch (IllegalArgumentException | IllegalAccessException e) {
//                    throw new JsonParseException(e);
//                }
//            }
        }
    }

    /**
     * Checks whether all super classes have all required fields.
     *
     * @param pojo Object to check required fields in its superclasses.
     *
     * @throws JsonParseException When some required field was not met.
     * */
    private void checkSuperClasses(@NonNull Object pojo) throws JsonParseException {
        Class<?> superclass = pojo.getClass();
        while ((superclass = superclass.getSuperclass()) != null) {
            checkFields(superclass.getDeclaredFields(), pojo);
        }
    }

}
