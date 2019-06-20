package com.hottop.permission;

import com.google.gson.Gson;
import com.hottop.core.model.user.Capability;
import com.hottop.core.model.user.Module;
import com.hottop.core.model.user.enums.EPermissionStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TestCapability {

    public static void main(String[] args) {
        createPermission_brand();
        //createRole();
    }

    public static void createPermission() {
        Capability capability = new Capability();
        capability.setPid(0l);
        capability.setDescription("分类管理");
        capability.setState(EPermissionStatus.ok.getStatus());
        capability.setUrl("/category/");
        capability.setMethod("POST");
        capability.setCapabilityName("category");

        System.out.println(new Gson().toJson(capability));
    }

    public static void createPermission_brand() {
        Capability capability = new Capability();
        capability.setPid(1l);
        capability.setDescription("查询品牌详情");
        capability.setState(EPermissionStatus.ok.getStatus());
        capability.setUrl("/brand/{\\d*}");
        capability.setMethod("GET");
        capability.setCapabilityName("brand_detail");

        System.out.println(new Gson().toJson(capability));
    }

    public static void createRole() {
        String roleStr = "{\n" +
                "            \"roleName\": \"ROLE_ADMIN\",\n" +
                "            \"state\": \"1\",\n" +
                "            \"remark\": \"超级管理员角色\",\n" +
                "            \"capabilities\": [],\n" +
                "            \"id\": 1,\n" +
                "            \"createTime\": \"2019-04-09 16:22:34\"\n" +
                "        }";
        Module module = new Gson().fromJson(roleStr, Module.class);
        module.setCreateTime(null);

        String pStr = "{\n" +
                "        \"permissionName\": \"brand\",\n" +
                "        \"state\": \"0\",\n" +
                "        \"description\": \"商标管理\",\n" +
                "        \"url\": \"/brand/\",\n" +
                "        \"pid\": 0,\n" +
                "        \"id\": 1,\n" +
                "        \"createTime\": \"2019-04-29 15:08:55\"\n" +
                "    }";
        Capability p1 = new Gson().fromJson(pStr, Capability.class);
        p1.setCreateTime(null);

        String pStr2 = "{\n" +
                "        \"permissionName\": \"category\",\n" +
                "        \"state\": \"0\",\n" +
                "        \"description\": \"分类管理\",\n" +
                "        \"url\": \"/category/\",\n" +
                "        \"pid\": 0,\n" +
                "        \"id\": 2,\n" +
                "        \"createTime\": \"2019-04-29 15:09:51\"\n" +
                "    }";

        Capability p2 = new Gson().fromJson(pStr2, Capability.class);
        p2.setCreateTime(null);

        ArrayList<Capability> capabilities = new ArrayList<>();
        capabilities.add(p1);
        capabilities.add(p2);
        module.setCapabilities(capabilities);

        System.out.println(new Gson().toJson(module));


    }
}
