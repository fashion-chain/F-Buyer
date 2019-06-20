package com.hottop.core.feature.type;

import com.hottop.core.feature.IIndicator;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeIndicator implements IIndicator<TypeMeta> {
    private String typeName;
    private ArrayList<TypeMeta> metas;

    TypeIndicator(String typeName) {
        this.typeName = typeName;
        this.metas = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public TypeIndicator registerMeta(TypeMeta... metas) {
        this.metas.addAll(Arrays.asList(metas));
        return this;
    }

    @Override
    public ArrayList<TypeMeta> metas() {
        return this.metas;
    }

    @Override
    public String name() {
        return this.typeName;
    }

}
