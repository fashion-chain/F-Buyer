package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.ComponentBase;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class TemplateContent implements Serializable {
    private ArrayList<TemplateComponent> components;

    private HashMap<String, TemplateComponent> features;
}
