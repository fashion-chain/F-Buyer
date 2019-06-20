package com.hottop.core.model.cms.bean.component.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class DataDecorator implements Serializable {
    private HashMap<Integer, ArrayList<ComponentDecorator>> decorators;
}
