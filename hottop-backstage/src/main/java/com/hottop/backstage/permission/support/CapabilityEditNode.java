package com.hottop.backstage.permission.support;

import com.hottop.core.model.user.Capability;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CapabilityEditNode extends Capability {

    private String title;

    private String value;

    private String key;

    private ArrayList<CapabilityEditNode> children = new ArrayList<>();

    public CapabilityEditNode() {
    }

}
