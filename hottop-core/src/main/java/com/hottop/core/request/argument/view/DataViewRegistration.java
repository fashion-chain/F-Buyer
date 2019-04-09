package com.hottop.core.request.argument.view;

import com.hottop.core.request.argument.view.exception.ViewExistsException;
import com.hottop.core.request.argument.view.exception.ViewNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class DataViewRegistration implements Serializable {
    private HashMap<Class, DataViewClassRegistration> viewMapper;

    public DataViewRegistration() {
        this.viewMapper = new HashMap<>();
    }

    public DataView getView(Class clazz, String viewName) throws ViewNotFoundException {
        if (viewMapper.containsKey(clazz)) {
            if (viewMapper.get(clazz).containsKey(viewName)) {
                return viewMapper.get(clazz).get(viewName);
            }
        }
        throw new ViewNotFoundException(clazz, viewName);
    }

    public DataViewClassRegistration clazz(Class clazz) {
        if (!viewMapper.containsKey(clazz)) {
            viewMapper.put(clazz, new DataViewClassRegistration(this, clazz));
        }
        return viewMapper.get(clazz);
    }

    public class DataViewClassRegistration extends HashMap<String, DataView> {
        private DataViewRegistration dataViewRegistration;
        private Class clazz;

        public Set<String> views() {
            return keySet();
        }

        private DataViewClassRegistration(DataViewRegistration dataViewRegistration, Class clazz) {
            this.dataViewRegistration = dataViewRegistration;
            this.clazz = clazz;
        }

        public DataViewClassRegistration addView(String viewName, DataView view) throws Exception {
            if (containsKey(viewName)) {
                throw new ViewExistsException(clazz, viewName);
            }
            put(viewName, view);
            return this;
        }

        public DataViewRegistration and() {
            return this.dataViewRegistration;
        }
    }
}
