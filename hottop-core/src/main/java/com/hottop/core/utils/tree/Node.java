package com.hottop.core.utils.tree;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Node implements TreeNodeInterface{

    private String label;

    private Long value;

    ArrayList<Node> children = new ArrayList<>();

    @Override
    public Long getParentId() {
        return null;
    }
}
