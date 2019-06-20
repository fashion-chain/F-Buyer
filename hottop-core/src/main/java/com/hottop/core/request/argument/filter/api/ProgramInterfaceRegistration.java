package com.hottop.core.request.argument.filter.api;

import com.hottop.core.request.argument.filter.exception.ProgramInterfaceNamespaceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProgramInterfaceRegistration implements Serializable {

    private HashMap<Class, ProgramInterfaceNamespaceRegistration> programInterfaceIndicatorMapper;
    private ArrayList<String> namespaces;

    public ProgramInterfaceRegistration() {
        this.programInterfaceIndicatorMapper = new HashMap<>();
        this.namespaces = new ArrayList<>();
    }

    public void registerNamespaces(String... namespaces) {
        this.namespaces.addAll(Arrays.asList(namespaces));
    }

    private boolean containsThisNamespace(String namespace) {
        return this.namespaces.contains(namespace);
    }

    public ProgramInterfaceIndicator getInterfaceIndicator(Class clazz, String namespace) {
        if (this.programInterfaceIndicatorMapper.containsKey(clazz) && this.programInterfaceIndicatorMapper.get(clazz).containsKey(namespace)) {
            return this.programInterfaceIndicatorMapper.get(clazz).get(namespace);
        }
        return null;
    }

    public ProgramInterfaceNamespaceRegistration clazz(Class<?> clazz) {
        if (!programInterfaceIndicatorMapper.containsKey(clazz)) {
            programInterfaceIndicatorMapper.put(clazz, new ProgramInterfaceNamespaceRegistration(this));
        }
        return programInterfaceIndicatorMapper.get(clazz);
    }

    public class ProgramInterfaceNamespaceRegistration extends HashMap<String, ProgramInterfaceIndicator> {
        private ProgramInterfaceRegistration programInterfaceRegistration;

        private ProgramInterfaceNamespaceRegistration(ProgramInterfaceRegistration programInterfaceRegistration) {
            this.programInterfaceRegistration = programInterfaceRegistration;
        }

        public ProgramInterfaceNamespaceRegistration registerNamespace(String namespace, ProgramInterfaceIndicator programInterfaceIndicator) throws Exception {
            if (!this.programInterfaceRegistration.containsThisNamespace(namespace)) {
                throw new ProgramInterfaceNamespaceException(String.format("namespace: %s not registered", namespace));
            }
            put(namespace, programInterfaceIndicator);
            return this;
        }

        public ProgramInterfaceRegistration and() {
            return this.programInterfaceRegistration;
        }
    }


    public ProgramInterfaceNamespaceRegistration apply(Class<?> clazz, String namespace, ProgramInterfaceIndicator programInterfaceIndicator) throws Exception{
        return this.clazz(clazz).registerNamespace(namespace, programInterfaceIndicator);
    }
}
