package com.hottop.backstage.permission.support;

import com.hottop.core.model.user.Capability;
import lombok.Data;

import java.util.ArrayList;

//权限树 node节点
@Data
public class CapabilityNode extends Capability {

    private Long key;

    private String name;

    private Long pId;

    private ArrayList<CapabilityNode> children = new ArrayList<>();

    public CapabilityNode() {
    }

    public CapabilityNode(Capability capability) {
        super(capability);
    }
}
