package com.hottop.core.model.cms.bean;

import com.hottop.core.model.cms.bean.component.ComponentBase;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class PageContent implements Serializable {
    private ArrayList<ComponentBase> components;

    private HashMap<String, ComponentBase> features;
}
