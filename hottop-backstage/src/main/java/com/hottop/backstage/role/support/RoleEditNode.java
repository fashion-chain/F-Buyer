package com.hottop.backstage.role.support;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RoleEditNode {

    private String title;

    private String value;

    private String key;

    private ArrayList<RoleEditNode> children = new ArrayList<>();

    public RoleEditNode() {
    }
}
