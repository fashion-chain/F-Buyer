package com.hottop.core.request.argument.view;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataView implements Serializable {
    public ArrayList<Attribute> attributes;

    private DataView(Attribute... attributes) {
        this.attributes = new ArrayList<>();
        addAttribute(attributes);
    }

    private DataView addAttribute(Attribute... attributes) {
        this.attributes.addAll(Arrays.asList(attributes));
        return this;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public ArrayList<String> getAttributeNames() {
        ArrayList<String> attributeNames = new ArrayList<>();
        for (Attribute attribute: this.attributes) {
            attributeNames.add(attribute.getName());
        }
        return attributeNames;
    }

    public static DataView create(Attribute... attributes) {
        return new DataView(attributes);
    }
}
