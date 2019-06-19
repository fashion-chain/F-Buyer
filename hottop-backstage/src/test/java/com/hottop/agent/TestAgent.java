package com.hottop.agent;

import com.google.gson.Gson;
import com.hottop.core.model.user.AgentModule;

//代理商
public class TestAgent {

    public static void main(String[] args) {
        createAgent();
    }

    //创建代理商
    private static void createAgent() {
        AgentModule agentModule = new AgentModule();
        agentModule.setUserId(1l);
        agentModule.setName("代理商1");
        agentModule.setRegionId(5l);
        agentModule.setLevel(3l);
        agentModule.setRebate(500l);
        agentModule.setDays(7);
        System.out.println(new Gson().toJson(agentModule));
    }
}
