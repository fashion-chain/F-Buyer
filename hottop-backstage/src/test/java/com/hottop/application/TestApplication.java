package com.hottop.application;

import com.google.gson.Gson;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.AgentModule;
import com.hottop.core.model.user.Application;
import com.hottop.core.model.user.ModuleBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//应用主体（代理商，后台用户，经销商，）
public class TestApplication {

    public static void main(String[] args) {
        addAgent();
    }

    //新增代理商
    public static void addAgent() {
        Application application = new Application();
        application.setPassword("root1234");
        application.setUsername("agent1");
        application.setTel("18312345678");
        application.setEmail("123@qq.com");
        application.setRemark("代理商1");

        AgentModule agentModule = new AgentModule();
        agentModule.setUserId(1l);
        agentModule.setName("代理商名称1");
        agentModule.setRegionId(5l);
        agentModule.setRegion("中国,beijing,北京市,朝阳区");
        agentModule.setLevel(3l);
        agentModule.setRebate(500l);
        //agentModule.setFoundTime(new Date());
        agentModule.setDays(7);

        ArrayList<ModuleBase> agentModules = new ArrayList<>();
        agentModules.add(agentModule);

        application.setModulesList(agentModules);

        System.out.println(new Gson().toJson(application));
    }
}
